package com.funtasty.fittester.injection.module

import android.content.Context
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.patloew.rxfit.RxFit
import com.thefuntasty.taste.injection.annotation.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleFitModule {

	@Provides
	@Singleton
	internal fun getRxFit(@ApplicationContext context: Context): RxFit {
		return RxFit(
				context,
				arrayOf(Fitness.CONFIG_API, Fitness.SENSORS_API, Fitness.HISTORY_API),
				arrayOf(Scope(Scopes.FITNESS_BODY_READ_WRITE), Fitness.SCOPE_BODY_READ_WRITE)
		)
	}
}
