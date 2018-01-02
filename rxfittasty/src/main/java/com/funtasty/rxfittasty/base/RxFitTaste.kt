package com.funtasty.rxfittasty.base

import android.content.Context
import com.funtasty.rxfittasty.history.HistoryApi
import com.funtasty.rxfittasty.util.ParcelablePair
import com.google.android.gms.fitness.FitnessOptions

class RxFitTaste(val context: Context, val fitnessDataTypesOptions: ArrayList<ParcelablePair>) {
	//	val ble = Fitness.getBleClient(context, GoogleSignIn.getLastSignedInAccount(context))
//	val config = Fitness.getConfigClient(context, GoogleSignIn.getLastSignedInAccount(context))
//	val goals = Fitness.getGoalsClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val history = HistoryApi(this)
//	val recording = Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
//	val sensors = Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context))
//	val sessions = Fitness.getSessionsClient(context, GoogleSignIn.getLastSignedInAccount(context))

	fun getFitnessOptions(): FitnessOptions {
		val builder = FitnessOptions.builder()
		for (pair in fitnessDataTypesOptions) {
			builder.addDataType(pair.dataType, pair.fitnessOptionsAccess)
		}
		return builder.build()
	}
}
