package com.funtasty.rxfittasty.base

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import rx.Single
import rx.SingleSubscriber

internal abstract class BaseSingle<T>(
		val rxFitTaste: RxFitTaste) : BaseRxTaste(rxFitTaste.context), Single.OnSubscribe<T> {

	private lateinit var subscriptionInfo: SingleSubscriber<in T>

	override fun call(t: SingleSubscriber<in T>) {
		subscriptionInfo = t
		createGoogleSignInClient(rxFitTaste.getFitnessOptions())
		if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(rxFitTaste.context), rxFitTaste.getFitnessOptions())) {
			resolvePermissions()
		} else {
			onGoogleApiClientReady(t)
		}
	}

	private fun resolvePermissions() {
		observableSet.add(this)

		if (!ResolutionActivity.isResolutionShown()) {
			val resolutionIntent = Intent(context, ResolutionActivity::class.java)
					.apply {
						putParcelableArrayListExtra(ResolutionActivity.DATA_TYPES_PAIRS, rxFitTaste.fitnessDataTypesOptions)
						flags = Intent.FLAG_ACTIVITY_NEW_TASK
					}
			context.startActivity(resolutionIntent)
		}
	}

	override fun handleResolutionResult(resultCode: Int) {
		if (resultCode == Activity.RESULT_OK) {
			call(subscriptionInfo)
		} else {
			subscriptionInfo.onError(Throwable("Google Fit Permission not granted"))
		}
	}

	abstract fun onGoogleApiClientReady(subscriber: SingleSubscriber<in T>)
}
