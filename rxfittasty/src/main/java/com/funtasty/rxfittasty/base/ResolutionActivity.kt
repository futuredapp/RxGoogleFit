package com.funtasty.rxfittasty.base

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.funtasty.rxfittasty.util.ParcelablePair
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions

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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
}