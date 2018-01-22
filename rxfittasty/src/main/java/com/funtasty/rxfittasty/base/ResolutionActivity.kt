package com.funtasty.rxfittasty.base

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import com.funtasty.rxfittasty.util.ParcelablePair
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.HealthDataTypes
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

internal class ResolutionActivity : Activity() {

	companion object {
		val DATA_TYPES_PAIRS = "data_types"

		fun isResolutionShown(): Boolean {
			return resolutionShown
		}

		private var resolutionShown = false
	}

	private val REQUEST_CODE_RESOLUTION = 123

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (savedInstanceState == null) {
			handleIntent()
		}
	}

	override fun onNewIntent(intent: Intent?) {
		setIntent(intent)
		handleIntent()
	}

	private fun handleIntent() {
		try {
			val pairs: ArrayList<ParcelablePair> = intent.getParcelableArrayListExtra(DATA_TYPES_PAIRS)
			val optionsBuilder: FitnessOptions.Builder = FitnessOptions.builder()
			for (pair in pairs) {
				optionsBuilder.addDataType(pair.dataType, pair.fitnessOptionsAccess)
			}

			val signInOptions = GoogleSignInOptions.Builder()
					.addExtension(optionsBuilder.build())
					.requestEmail()
					.build()

			val signInClient = GoogleSignIn.getClient(this.applicationContext, signInOptions)
			Log.i("ResolutionActivity", "GoogleClientID id: ${signInClient.instanceId}")
//			startActivityForResult(signInClient.signInIntent, REQUEST_CODE_RESOLUTION)

			GoogleSignIn.requestPermissions(
					this,
					REQUEST_CODE_RESOLUTION,
					GoogleSignIn.getLastSignedInAccount(this.applicationContext),
					optionsBuilder.build())

			resolutionShown = true
		} catch (e: IntentSender.SendIntentException) {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		} catch (e: NullPointerException) {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQUEST_CODE_RESOLUTION) {

			val fitClient = Fitness.getHistoryClient(this.applicationContext, GoogleSignIn.getLastSignedInAccount(this.applicationContext))
			Log.i("ResolutionActivity", "FitnessClientId: ${fitClient.instanceId}")
			fitClient.readData(bloodGlucoseRequest)
					.addOnCompleteListener {
						if (it.isSuccessful && it.result.status.isSuccess) {
							Log.d("Fitness data", "success: ${it.isSuccessful} statusCode: ${it.result.status.statusCode}")
							setResolutionResultAndFinish(resultCode)
						} else {
							Log.e("Fitness data", "success: ${it.isSuccessful} statusCode: ${it.result.status.statusCode}")
							setResolutionResultAndFinish(resultCode)
						}
					}
					.addOnFailureListener {
						Log.d("Fitness data", "error: ${it.message}")
						setResolutionResultAndFinish(resultCode)
					}

		} else {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		}
	}

	private fun setResolutionResultAndFinish(resultCode: Int) {
		resolutionShown = false
		BaseRxTaste.onResolutionResult(resultCode)
		finish()
	}

	val bloodGlucoseRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(HealthDataTypes.TYPE_BLOOD_GLUCOSE)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	private fun now(): Long {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
	}
}