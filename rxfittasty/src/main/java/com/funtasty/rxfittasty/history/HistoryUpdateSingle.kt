package com.funtasty.rxfittasty.history

import android.util.Log
import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataUpdateRequest
import rx.SingleSubscriber

internal class HistoryUpdateSingle(rxFit: RxFitTaste, private val dataUpdateRequest: DataUpdateRequest) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		val fitUpdateClient = Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, rxFitTaste.getFitnessOptions()))
		fitUpdateClient
				.updateData(dataUpdateRequest)
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSafeSuccess(it.result)
					} else {
						Log.e("HistoryUpdateSingle", "unsuccessful")
						subscriber.onSafeError(it.exception as Throwable)
					}
				}
				.addOnFailureListener {
					subscriber.onSafeError(it)
				}
	}
}
