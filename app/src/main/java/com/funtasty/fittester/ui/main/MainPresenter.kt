package com.funtasty.fittester.ui.main

import android.content.Context
import com.funtasty.fittester.data.store.FitnessStore
import com.funtasty.fittester.rxFitTasty.base.RxFitTaste
import com.funtasty.fittester.rxFitTasty.history.HistoryApi
import com.funtasty.fittester.rxFitTasty.util.ParcalablePair
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.thefuntasty.taste.injection.annotation.scope.PerScreen
import com.thefuntasty.taste.mvp.BasePresenter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

	@Inject lateinit var fitnessStore: FitnessStore

	override fun attachView(mvpView: MainView?) {
		super.attachView(mvpView)
		Timber.d("attachView")

	}

	override fun detachView() {
		super.detachView()
		Timber.d("detachView")
	}

	fun onGetData(accessGranted: Boolean, context: Context) {
		if (accessGranted) {
			getData(context)
		} else {
			view.getPerms()
		}
	}

	private fun getData(context: Context) {
//		fitnessStore.bloodGlucose

		val types = ArrayList<ParcalablePair>()
		types.add(ParcalablePair(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE))
		val rxFitTaste = RxFitTaste(context, types)
		val history = HistoryApi(rxFitTaste)
		history.read(fitnessStore.heightRequest)
//		val historySingle = Single.create(HistoryReadSingle(rxFitTaste, fitnessStore.heightRequest))
//		historySingle
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe({
					Timber.d(it.status.toString())
					view.setStatusText(it.status.toString())
				}, {
					Timber.e(it.message)
				})
//				.subscribe { result: DataReadResult?, t2: Throwable? ->
//					result?.let {
//						Timber.d(it.status.toString())
//						view.setStatusText(it.status.toString())
//					}
//
//					t2?.let {
//						Timber.e(it.message)
//						if (it is GoogleAPIConnectionException) {
//							if (it.connectionResult.hasResolution()) {
//								view.handleResolution(it)
//							}
//						}
//					}
//				}
	}
}