package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import rx.SingleSubscriber
import timber.log.Timber

class RevokeAccessSingle(rxFit: RxFitTaste) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		val signInClient = createGoogleSignInClient(rxFitTaste.getFitnessOptions())
		signInClient.revokeAccess()
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSuccess(it.result)
					}
				}
				.addOnFailureListener {
					subscriber.onError(it)
				}
	}
}





