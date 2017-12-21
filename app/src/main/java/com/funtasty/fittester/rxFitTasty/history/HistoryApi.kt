package com.funtasty.fittester.rxFitTasty.history

import android.support.annotation.NonNull
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.Single

class HistoryApi(val rxFitTaste: RxFitTaste) {


	private fun read(@NonNull dataReadRequest: DataReadRequest): Single<DataReadResponse> {

		return Single.just(DataReadResponse())
//		return Single.create( HistorySingle(rxFitTaste,dataReadRequest))
	}
}