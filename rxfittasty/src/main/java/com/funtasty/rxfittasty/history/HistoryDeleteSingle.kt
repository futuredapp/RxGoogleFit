package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.request.DataDeleteRequest
import rx.SingleSubscriber

class HistoryDeleteSingle(val rxFit: RxFitTaste, val dataDeleteRequest: DataDeleteRequest) : BaseSingle<Void>(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		rxFitTaste.history.deleteData(dataDeleteRequest)
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