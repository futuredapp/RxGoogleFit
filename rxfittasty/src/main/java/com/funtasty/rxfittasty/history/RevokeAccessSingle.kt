package com.funtasty.rxfittasty.history

import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import rx.SingleSubscriber

class RevokeAccessSingle(rxFit: RxFitTaste) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		val signInClient = createGoogleSignInClient(rxFitTaste.getFitnessOptions())
		signInClient.signOut() // TODO signout or revoke?
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSafeSuccess(it.result)
					} else {
						subscriber.onSafeError(it.exception as Throwable)
					}
				}
				.addOnFailureListener {
					subscriber.onSafeError(it)
				}
	}
}





