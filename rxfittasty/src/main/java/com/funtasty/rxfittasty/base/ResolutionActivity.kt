package com.funtasty.rxfittasty.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.funtasty.rxfittasty.util.ParcelablePair
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.HealthDataTypes
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.tasks.Task
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

	private val REQUEST_PERMISSIONS_RESOLUTION = 123
	private val REQUEST_ACCOUNT_RESOLUTION = 1234
	private lateinit var signInOptions: GoogleSignInOptions
	private lateinit var signInOptionsExt: GoogleSignInOptionsExtension

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
		val pairs: ArrayList<ParcelablePair> = intent.getParcelableArrayListExtra(DATA_TYPES_PAIRS)
		val optionsBuilder: FitnessOptions.Builder = FitnessOptions.builder()
		for (pair in pairs) {
			optionsBuilder.addDataType(pair.dataType, pair.fitnessOptionsAccess)
		}
		signInOptionsExt = optionsBuilder.build()
		signInOptions = GoogleSignInOptions.Builder()
				.addExtension(signInOptionsExt)
				.build()


//		val account = GoogleSignIn.getLastSignedInAccount(this.applicationContext) 						// not working
//		val account = getAccount(signInOptions)															// not working
		val account = GoogleSignIn.getAccountForExtension(this.applicationContext, signInOptionsExt)    // not working

		requestPermisions(account, signInOptionsExt)
	}

	private fun getAccount(signInOptions: GoogleSignInOptions) {
		val signInClient = GoogleSignIn.getClient(this.applicationContext, signInOptions)
		startActivityForResult(signInClient.signInIntent, REQUEST_ACCOUNT_RESOLUTION)

	}

	private fun requestPermisions(account: GoogleSignInAccount, signInOptions: GoogleSignInOptionsExtension) {
		if (GoogleSignIn.hasPermissions(account, signInOptions)) {
			setResolutionResultAndFinish(Activity.RESULT_OK)
		} else {
			GoogleSignIn.requestPermissions(
					this,
					REQUEST_PERMISSIONS_RESOLUTION,
					account,
					signInOptions)

			resolutionShown = true
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		when (requestCode) {
			REQUEST_PERMISSIONS_RESOLUTION -> {
				tryGetData()
				setResolutionResultAndFinish(resultCode)
			}
			REQUEST_ACCOUNT_RESOLUTION -> {
				handleAccountRequest(requestCode, data)
			}
			else -> {
				setResolutionResultAndFinish(Activity.RESULT_CANCELED)
			}

		}
	}

	private fun handleAccountRequest(requestCode: Int, data: Intent?) {
		val task = GoogleSignIn.getSignedInAccountFromIntent(data)
		handleSignInResult(task)
	}

	private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
		try {
			val account = task.getResult(ApiException::class.java)
			requestPermisions(account, signInOptionsExt)
		} catch (e: ApiException) {
			Log.e("ResolutionActivity", "handleAccountRequest: ${e.message}")
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		}
	}


	private fun tryGetData() {
		val account = GoogleSignIn.getAccountForExtension(this.applicationContext, signInOptionsExt)

		val fitClient = Fitness.getHistoryClient(this.applicationContext, account)
		fitClient.readData(bloodGlucoseRequest)
				.addOnCompleteListener {
					if (it.isSuccessful && it.result.status.isSuccess) {
						Log.d("Fitness data", "success: ${it.isSuccessful} statusCode: ${it.result.status.statusCode}")
					} else {
						Log.e("Fitness data", "success: ${it.isSuccessful} statusCode: ${it.result.status.statusCode}")
					}
				}
				.addOnFailureListener {
					Log.d("Fitness data", "error: ${it.message}")
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