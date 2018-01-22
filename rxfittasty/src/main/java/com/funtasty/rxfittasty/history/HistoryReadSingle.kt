package com.funtasty.rxfittasty.history

import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import rx.SingleSubscriber

internal class HistoryReadSingle(
		rxFit: RxFitTaste,
		private val dataReadRequest: DataReadRequest) : BaseSingle<DataReadResponse>(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in DataReadResponse>) {

		Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context)) //TODO Application context
				.readData(dataReadRequest)
				.addOnCompleteListener {
					if (it.isSuccessful && it.result.status.isSuccess) {
						subscriber.onSafeSuccess(it.result)
					} else {
						subscriber.onSafeError(it.exception)
					}
				}
				.addOnFailureListener {
					subscriber.onSafeError(it)
				}
	}
}
