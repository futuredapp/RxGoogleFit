package com.funtasty.rxfittasty.history

import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataUpdateRequest
import rx.SingleSubscriber

class HistoryUpdateSingle(rxFit: RxFitTaste, private val dataUpdateRequest: DataUpdateRequest) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
				.updateData(dataUpdateRequest)
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSuccess(it.result)
					} else {
						subscriber.onError(it.exception)
					}
				}
				.addOnFailureListener {
					subscriber.onError(it)
				}
	}
}
