package com.funtasty.fittester.injection.module

import android.content.Context
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.ParcalablePair
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes
import com.thefuntasty.taste.injection.annotation.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleFitModule {

	@Provides
	@Singleton
	internal fun getRxFitTaste(@ApplicationContext context: Context): RxFitTaste {
		val types = ArrayList<ParcalablePair>()
		types.add(ParcalablePair(HealthDataTypes.TYPE_BLOOD_GLUCOSE, FitnessOptions.ACCESS_READ))
		types.add(ParcalablePair(HealthDataTypes.TYPE_BLOOD_PRESSURE, FitnessOptions.ACCESS_READ))
		types.add(ParcalablePair(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE))
		types.add(ParcalablePair(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE))
		types.add(ParcalablePair(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE))
		return RxFitTaste(context, types)
	}
}
