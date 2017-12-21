package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.SingleSubscriber

class HistorySingle(val rxFit: RxFitTaste, val dataReadRequest: DataReadRequest) : BaseSingle(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in DataReadResponse>) {
		rxFitTaste.history.readData(dataReadRequest)
				.addOnCompleteListener {
					if (it.isSuccessful && it.result.status.isSuccess) {
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
