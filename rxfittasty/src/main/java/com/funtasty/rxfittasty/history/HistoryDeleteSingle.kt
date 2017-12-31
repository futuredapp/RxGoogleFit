package com.funtasty.rxfittasty.history

import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataDeleteRequest
import rx.SingleSubscriber

class HistoryDeleteSingle(
		rxFit: RxFitTaste,
		private val dataDeleteRequest: DataDeleteRequest) : BaseSingle<Void>(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
				.deleteData(dataDeleteRequest)
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSafeSuccess(it.result)
					} else {
						subscriber.onSafeError(it.exception as Throwable)
					}
				}
				.addOnFailureListener { subscriber.onSafeError(it) }
	}
}
