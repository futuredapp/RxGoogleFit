package com.funtasty.fittester.rxFitTasty.base

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Parcelable
import com.funtasty.fittester.rxFitTasty.util.ParcalablePair
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class ResolutionActivity : Activity() {

	companion object {
		val DATA_TYPES_PAIRS = "data_types"
	}

	private val REQUEST_CODE_RESOLUTION = 123

	private var resolutionShown = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (savedInstanceState == null) {
			handleIntent()
		}
	}

	override fun onNewIntent(intent: Intent) {
		setIntent(intent)
		handleIntent()
	}

	private fun handleIntent() {
		try {
			val pairs: ArrayList<ParcalablePair> = intent.getParcelableArrayListExtra(DATA_TYPES_PAIRS)
			val optionsBuilder: FitnessOptions.Builder = FitnessOptions.builder()
			for (pair in pairs) {
				optionsBuilder.addDataType(pair.first, pair.second)
			}

			val signInOptions = GoogleSignInOptions.Builder()
					.addExtension(optionsBuilder.build())
					.build()

			val signInIntent = GoogleSignIn.getClient(this, signInOptions).signInIntent
			startActivityForResult(signInIntent, REQUEST_CODE_RESOLUTION)
			resolutionShown = true
		} catch (e: IntentSender.SendIntentException) {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		} catch (e: NullPointerException) {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		if (requestCode == REQUEST_CODE_RESOLUTION) {
			setResolutionResultAndFinish(resultCode)
		} else {
			setResolutionResultAndFinish(Activity.RESULT_CANCELED)
		}
	}

	private fun setResolutionResultAndFinish(resultCode: Int) {
		resolutionShown = false
		BaseRxTaste.onResolutionResult(resultCode)
		finish()
	}

	internal fun isResolutionShown(): Boolean {
		return resolutionShown
	}
}