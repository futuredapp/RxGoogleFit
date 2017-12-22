package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.SingleSubscriber

class HistoryReadSingle(rxFit: RxFitTaste, private val dataReadRequest: DataReadRequest) : BaseSingle<DataReadResponse>(rxFit) {

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
