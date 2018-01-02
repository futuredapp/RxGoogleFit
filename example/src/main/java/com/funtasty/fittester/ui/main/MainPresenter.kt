package com.funtasty.fittester.ui.main

import com.funtasty.fittester.data.store.FitnessStore
import com.funtasty.rxfittasty.base.RxFitTaste
import com.funtasty.rxfittasty.util.ParcelablePair
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.HealthDataTypes
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
			val types = ArrayList<ParcelablePair>()
			types.add(ParcelablePair(HealthDataTypes.TYPE_BLOOD_GLUCOSE, FitnessOptions.ACCESS_READ))
			val requests = arrayOf(fitnessStore.heightRequest, fitnessStore.weightRequest, fitnessStore.heartRateRequest, fitnessStore.bloodGlucoseRequest, fitnessStore.bloodPressureRequest)
			rxFitTaste.history.read(requests[i])
					.observeOn(AndroidSchedulers.mainThread())
					.subscribeOn(Schedulers.io())
					.subscribe({
						Timber.d("${it.status} ${requests[i].dataTypes[0].name}")
						view.setStatusText(it.status.toString())
						getData()

					}, {
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
