package com.funtasty.fittester.ui.main

import com.funtasty.fittester.data.store.FitnessStore
import com.funtasty.rxfittasty.base.RxFitTaste
import com.thefuntasty.taste.injection.annotation.scope.PerScreen
import com.thefuntasty.taste.mvp.BasePresenter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

	@Inject lateinit var fitnessStore: FitnessStore
	@Inject lateinit var rxFitTaste: RxFitTaste
	var i: Int = 0

	override fun attachView(mvpView: MainView?) {
		super.attachView(mvpView)
		Timber.d("attachView")

	}

	override fun detachView() {
		super.detachView()
		Timber.d("detachView")
	}

	fun onGetData(accessGranted: Boolean) {
		if (accessGranted) {
			getData()
		} else {
			view.getPerms()
		}
	}

	private fun getData() {
		if (i <= 4) {
			val requests = arrayOf(fitnessStore.bloodGlucoseRequest, fitnessStore.weightRequest, fitnessStore.heartRateRequest, fitnessStore.heightRequest, fitnessStore.bloodPressureRequest)
			rxFitTaste.history.read(requests[i])
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.io())
					.subscribe({
						Timber.d("${it.status} ${requests[i].dataTypes[0].name}")
						view.setStatusText(it.status.toString())
						i++
						getData()

					}, {
						Timber.e(it.message)
					})

		} else {
			i = 0
			setData()
		}
	}

	private fun setData() {
		val requests = arrayOf(fitnessStore.getHeightDataSet(1.2f, fitnessStore.now()), fitnessStore.getWeightDataSet(120f, fitnessStore.now()))
		if (i <= 1) {
			rxFitTaste.history.insert(requests[i])
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.io())
					.subscribe({
						Timber.d(" ${requests[i].dataType.name}")
						view.setStatusText("insert: ${requests[i].dataType.name} OK")
						i++
						setData()
					}, {
						i++
						Timber.e(it.message)
					})

		} else {
			i = 0
		}
	}

	fun revokeAccess() {
		rxFitTaste.history.revokeAccess()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe({
					Timber.d("revoked")
				}, {
					Timber.e(it.message)
				})
	}
}
