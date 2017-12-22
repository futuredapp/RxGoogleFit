package com.funtasty.fittester.rxFitTasty.history

import com.funtasty.fittester.rxFitTasty.base.BaseSingle
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.data.DataSet
import rx.SingleSubscriber

class HistoryInsertSingle(rxFit: RxFitTaste, private val insertDataSet: DataSet) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		rxFitTaste.history.insertData(insertDataSet)
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