package com.funtasty.fittester.injection.component

import com.funtasty.fittester.injection.module.ApplicationModule
import com.funtasty.fittester.injection.module.GoogleFitModule
import com.funtasty.fittester.ui.main.MainActivityComponent
import com.thefuntasty.taste.injection.module.TasteModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(ApplicationModule::class, TasteModule::class, GoogleFitModule::class))
@Singleton
interface ApplicationComponent {
	fun plus(module: MainActivityComponent.MainActivityModule): MainActivityComponent
}