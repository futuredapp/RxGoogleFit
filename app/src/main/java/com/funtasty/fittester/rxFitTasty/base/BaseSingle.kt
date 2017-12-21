package com.funtasty.fittester.rxFitTasty.base

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.result.DataReadResponse
import rx.Single
import rx.SingleSubscriber

abstract class BaseSingle(val rxFitTaste: RxFitTaste) : Single.OnSubscribe<DataReadResponse>, BaseRxTaste(rxFitTaste.context) {

	val subscriptionInfoMap: List<SingleSubscriber<DataReadResponse>> = ArrayList()


	override fun call(t: SingleSubscriber<in DataReadResponse>) {
		subscriptionInfoMap.plus(t)
		val googleSignInClient = createGoogleSignInClient(rxFitTaste.getFitnessOptions())
		if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(rxFitTaste.context), rxFitTaste.getFitnessOptions())) {
			resolvePermissions()
		} else {
			onGoogleApiClientReady(t)
		}
	}

	private fun resolvePermissions() {
		observableSet.add(this)

		val resolutionIntent = Intent(context, ResolutionActivity::class.java)
				.apply {
					putExtra("resolution", true)
					putParcelableArrayListExtra(ResolutionActivity.DATA_TYPES_PAIRS, rxFitTaste.fitnessDataTypesOptions)
					flags = Intent.FLAG_ACTIVITY_NEW_TASK
				}
		context.startActivity(resolutionIntent)
	}

	override fun handleResolutionResult(resultCode: Int) {
		if (resultCode == Activity.RESULT_OK) {
			for (t in subscriptionInfoMap) {
				call(t)
			}
		}
	}

	abstract fun onGoogleApiClientReady(subscriber: SingleSubscriber<in DataReadResponse>)
}