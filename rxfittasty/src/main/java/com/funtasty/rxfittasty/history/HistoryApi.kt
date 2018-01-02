package com.funtasty.rxfittasty.history

import android.support.annotation.NonNull
import com.funtasty.rxfittasty.base.RxFitTaste
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.request.DataDeleteRequest
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataUpdateRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.Single
import rx.schedulers.Schedulers

class HistoryApi(private val rxFitTaste: RxFitTaste) {

	fun read(@NonNull dataReadRequest: DataReadRequest): Single<DataReadResponse> {
		return Single.create(HistoryReadSingle(rxFitTaste, dataReadRequest))
				.observeOn(Schedulers.io())
	}

	fun delete(@NonNull dataDeleteRequest: DataDeleteRequest): Single<Void> {
		return Single.create(HistoryDeleteSingle(rxFitTaste, dataDeleteRequest))
				.observeOn(Schedulers.io())
	}

	fun update(@NonNull dataUpdateRequest: DataUpdateRequest): Single<Void> {
		return Single.create(HistoryUpdateSingle(rxFitTaste, dataUpdateRequest))
				.observeOn(Schedulers.io())
	}

	fun insert(@NonNull insertDataSet: DataSet): Single<Void> {
		return Single.create(HistoryInsertSingle(rxFitTaste, insertDataSet))
				.observeOn(Schedulers.io())
	}

	fun revokeAccess(): Single<Void> {
		return Single.create(RevokeAccessSingle(rxFitTaste))
				.observeOn(Schedulers.io())
	}
}
