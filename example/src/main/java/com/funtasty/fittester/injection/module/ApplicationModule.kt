package com.funtasty.fittester.injection.module

import android.app.Application
import android.content.Context

import com.thefuntasty.taste.injection.annotation.qualifier.ApplicationContext

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

	@Provides
	@Singleton
	@ApplicationContext
	fun context(): Context {
		return application
	}
}
