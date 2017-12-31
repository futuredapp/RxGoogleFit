package com.funtasty.rxfittasty.history

import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.fitness.result.DataReadResult
import rx.SingleSubscriber

class HistoryReadSingle(rxFit: RxFitTaste, private val dataReadRequest: DataReadRequest):
		BaseSingle<DataReadResponse>(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in DataReadResponse>) {

		Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
				.readData(dataReadRequest)
				.addOnCompleteListener {
					if (it.isSuccessful && it.result.status.isSuccess) {
//						subscriber.isUnsubscribed TODO

						subscriber.onSuccess(it.result)
					} else {
						subscriber.onError(it.exception)
					}
				}
				.addOnFailureListener {
					//	subscriber.isUnsubscribed TODO
					subscriber.onError(it)
				}
	}
}
