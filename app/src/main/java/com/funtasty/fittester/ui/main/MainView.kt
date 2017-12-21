package com.funtasty.fittester.ui.main

import com.patloew.rxfit.GoogleAPIConnectionException
import com.thefuntasty.taste.mvp.MvpView

interface MainView : MvpView {
	fun setStatusText(text: String)

	fun getPerms()

	fun handleResolution(exception: GoogleAPIConnectionException)

}