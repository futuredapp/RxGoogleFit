package com.funtasty.fittester.rxFitTasty.base

import android.content.Context
import com.funtasty.fittester.rxFitTasty.util.ParcalablePair
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class RxFitTaste(val context: Context, val fitnessDataTypesOptions: ArrayList<ParcalablePair> ) {
	val ble = Fitness.getBleClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val config = Fitness.getConfigClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val goals = Fitness.getGoalsClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val history = Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val recording = Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val sensors = Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context))
	val sessions = Fitness.getSessionsClient(context, GoogleSignIn.getLastSignedInAccount(context))

	fun getFitnessOptions(): FitnessOptions {
		val builder = FitnessOptions.builder()
		for (pair in fitnessDataTypesOptions) {
			builder.addDataType(pair.first , pair.second)
		}
		return builder.build()
	}
}