package com.funtasty.fittester

import android.app.Application
import com.funtasty.fittester.injection.component.ApplicationComponent
import com.funtasty.fittester.injection.component.DaggerApplicationComponent
import com.funtasty.fittester.injection.module.ApplicationModule
import timber.log.Timber

class App : Application() {

	companion object {
		lateinit var applicationComponent: ApplicationComponent
	}

	override fun onCreate() {
		super.onCreate()

		applicationComponent = DaggerApplicationComponent
				.builder()
				.applicationModule(ApplicationModule(this))
				.build()
		if (BuildConfig.DEBUG) {
			Timber.plant(timber.log.Timber.DebugTree())
		}
	}


}
