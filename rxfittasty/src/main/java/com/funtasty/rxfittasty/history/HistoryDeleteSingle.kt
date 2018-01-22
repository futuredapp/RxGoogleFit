package com.funtasty.rxfittasty.history

import android.util.Log
import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.request.DataDeleteRequest
import rx.SingleSubscriber

internal class HistoryDeleteSingle(
		rxFit: RxFitTaste,
		private val dataDeleteRequest: DataDeleteRequest) : BaseSingle<Void>(rxFit) {

	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {

		val fitDeleteClient = Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
		Log.i("HistoryDeleteSingle", "clientId: ${fitDeleteClient.instanceId} googleApiClient connected: ${fitDeleteClient.zzago().isConnected}")
		fitDeleteClient
				.deleteData(dataDeleteRequest)
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSafeSuccess(it.result)
					} else {
						Log.e("HistoryDeleteSingle","unsuccessful")
						subscriber.onSafeError(it.exception as Throwable)
					}
				}
				.addOnFailureListener { subscriber.onSafeError(it) }
	}
}
