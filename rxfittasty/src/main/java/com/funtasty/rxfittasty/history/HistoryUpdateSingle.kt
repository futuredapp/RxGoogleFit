package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.request.DataUpdateRequest
import rx.SingleSubscriber

class HistoryUpdateSingle(rxFit: RxFitTaste, private val dataUpdateRequest: DataUpdateRequest) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		rxFitTaste.history.updateData(dataUpdateRequest)
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