package com.funtasty.rxfittasty.history

import android.support.annotation.NonNull
import com.funtasty.rxfittasty.base.RxFitTaste
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.request.DataDeleteRequest
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataUpdateRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.Single

class HistoryApi(private val rxFitTaste: RxFitTaste) {

	fun read(@NonNull dataReadRequest: DataReadRequest): Single<DataReadResponse> {
		return Single.create(HistoryReadSingle(rxFitTaste, dataReadRequest))
	}

	fun delete(@NonNull dataDeleteRequest: DataDeleteRequest): Single<Void> {
		return Single.create(HistoryDeleteSingle(rxFitTaste, dataDeleteRequest))
	}

	fun update(@NonNull dataUpdateRequest: DataUpdateRequest): Single<Void> {
		return Single.create(HistoryUpdateSingle(rxFitTaste, dataUpdateRequest))
	}

	fun insert(@NonNull insertDataSet: DataSet): Single<Void> {
		return Single.create(HistoryInsertSingle(rxFitTaste, insertDataSet))
	}

	fun revokeAccess(): Single<Void> {
		return Single.create(RevokeAccessSingle(rxFitTaste))
	}
}
