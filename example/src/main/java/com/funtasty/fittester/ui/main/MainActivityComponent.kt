package com.funtasty.fittester.ui.main

import com.thefuntasty.taste.injection.annotation.scope.PerScreen
import com.thefuntasty.taste.injection.component.BaseActivityComponent
import com.thefuntasty.taste.injection.module.BaseActivityModule
import dagger.Module
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainActivityComponent.MainActivityModule::class))
@PerScreen
interface MainActivityComponent : BaseActivityComponent<MainActivity> {

	@Module
	class MainActivityModule internal constructor(activity: MainActivity) :
			BaseActivityModule<MainActivity>(activity)
}