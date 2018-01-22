package com.funtasty.rxfittasty.history

import android.util.Log
import com.funtasty.rxfittasty.base.BaseSingle
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.onSafeError
import com.funtasty.rxfittasty.util.onSafeSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataSet
import rx.SingleSubscriber

internal class HistoryInsertSingle(rxFit: RxFitTaste, private val insertDataSet: DataSet) : BaseSingle<Void>(rxFit) {
	override fun onGoogleApiClientReady(subscriber: SingleSubscriber<in Void>) {
		val fitInsertClient = Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
		Log.i("HistoryInsertSingle", "clientId: ${fitInsertClient.instanceId} googleApiClient connected: ${fitInsertClient.zzago().isConnected}")
		fitInsertClient.insertData(insertDataSet)
				.addOnCompleteListener {
					if (it.isSuccessful) {
						subscriber.onSafeSuccess(it.result)
					} else {
						Log.e("HistoryInsertSingle","unsuccessful")
						subscriber.onSafeError(it.exception)
					}
				}
				.addOnFailureListener {
					subscriber.onSafeError(it)
				}
	}
}
