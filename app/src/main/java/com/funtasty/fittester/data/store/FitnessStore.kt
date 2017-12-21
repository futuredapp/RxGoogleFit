package com.funtasty.fittester.data.store

import com.funtasty.fittester.data.model.ui.MeasurementType
import com.funtasty.fittester.tools.Constant
import com.google.android.gms.common.api.Status
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes
import com.google.android.gms.fitness.request.DataDeleteRequest
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataUpdateRequest
import com.google.android.gms.fitness.result.DataReadResult
import com.patloew.rxfit.RxFit
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitnessStore @Inject constructor(private var rxFit: RxFit) {

//	val permission: Observable<Status>
//		get() = checkFitConnection().andThen<Any>(
//				Observable.combineLatest(
//						fitHeight.map<Any>(Func1<DataReadResult, Any> { it.getStatus() }),
//						fitWeight.map<Any>(Func1<DataReadResult, Any> { it.getStatus() }),
//						fitHeartRate.map<Any>(Func1<DataReadResult, Any> { it.getStatus() }),
//						bloodPressure.map<Any>(Func1<DataReadResult, Any> { it.getStatus() }),
//						bloodGlucose.map<Any>(Func1<DataReadResult, Any> { it.getStatus() })
//
//				) { height, weight, heartrate, pressure, glucose ->
//
//					if (!height.isSuccess()) {
//						return@Observable.combineLatest height . getStatus ()
//					}
//					if (!weight.isSuccess()) {
//						return@Observable.combineLatest weight . getStatus ()
//					}
//					if (!heartrate.isSuccess()) {
//						return@Observable.combineLatest heartrate . getStatus ()
//					}
//					if (!pressure.isSuccess()) {
//						return@Observable.combineLatest pressure . getStatus ()
//					}
//					if (!glucose.isSuccess()) {
//						return@Observable.combineLatest glucose . getStatus ()
//					}
//
//					glucose.getStatus()
//				}
//		)

	val bloodPressure: Single<DataReadResult>
		get() = readHistory(bloodPressureRequest)

	val bloodGlucose: Single<DataReadResult>
		get() = readHistory(bloodGlucoseRequest)

	val fitHeight: Single<DataReadResult>
		get() = readHistory(heightRequest)

	val fitWeight: Single<DataReadResult>
		get() = readHistory(weightRequest)

	val fitHeartRate: Single<DataReadResult>
		get() = readHistory(heartRateRequest)

	val bloodGlucoseRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(HealthDataTypes.TYPE_BLOOD_GLUCOSE)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	private val bloodPressureRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(HealthDataTypes.TYPE_BLOOD_PRESSURE)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	// used to get height data from Fitness api
	val heightRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(DataType.TYPE_HEIGHT)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	// used to get weight data from Fitness api
	private val weightRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(DataType.TYPE_WEIGHT)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	// used to get heartrate data from Fitness api
	private val heartRateRequest: DataReadRequest
		get() = DataReadRequest.Builder()
				.read(DataType.TYPE_HEART_RATE_BPM)
				.setTimeRange(1, now(), TimeUnit.MILLISECONDS)
				.build()

	fun revokeFitAccess(): Single<Status> {
		return rxFit.config().disableFit()
				.observeOn(Schedulers.io())
	}


	fun checkFitConnection(): Completable {
		return rxFit.checkConnection()
		//				.observeOn(Schedulers.io());
	}

	fun saveWeight(weight: Float?, date: Long): Single<Status> {
		return insertHistoryDataSet(getWeightDataSet(weight, date))
	}

	fun saveHeight(height: Float?, date: Long): Single<Status> {
		return insertHistoryDataSet(getHeightDataSet(height, date))
	}

	fun updateHeight(height: Float?, date: Long): Single<Status> {
		return updateHistoryDataSet(getHeightUpdateRequest(height, date))
	}

	fun updateWeight(weight: Float?, date: Long): Single<Status> {
		return updateHistoryDataSet(getWeightUpdateRequest(weight, date))
	}

	fun deleteHeight(date: Long): Single<Status> {
		return deleteHistoryData(getDataDeleteRequest(DataType.TYPE_HEIGHT, date))
	}

	fun deleteWeight(date: Long): Single<Status> {
		return deleteHistoryData(getDataDeleteRequest(DataType.TYPE_WEIGHT, date))
	}

	private fun deleteHistoryData(request: DataDeleteRequest): Single<Status> {
		return rxFit.history().delete(request)
				.observeOn(Schedulers.io())
	}

	private fun getDataDeleteRequest(type: DataType, date: Long): DataDeleteRequest {
		return DataDeleteRequest.Builder()
				.setTimeInterval(1L, date, TimeUnit.MILLISECONDS)
				.addDataType(type)
				.build()
	}

	private fun getWeightUpdateRequest(weight: Float?, date: Long): DataUpdateRequest {
		return getDataUpdateRequest(getWeightDataSet(weight, date), date)
	}

	private fun getHeightUpdateRequest(height: Float?, date: Long): DataUpdateRequest {
		return getDataUpdateRequest(getHeightDataSet(height, date), date)
	}

	private fun getDataUpdateRequest(dataSet: DataSet, date: Long): DataUpdateRequest {
		return DataUpdateRequest.Builder()
				.setDataSet(dataSet)
				.setTimeInterval(date, date, TimeUnit.MILLISECONDS)
				.build()
	}

	private fun readHistory(readRequest: DataReadRequest): Single<DataReadResult> {
		return rxFit.history().read(readRequest)
				.observeOn(Schedulers.io())
	}

	fun getRed() {
		//		DataReadResult readResult = new DataReadResult();
		//		readResult.getStatus().getResolution()
	}

	private fun insertHistoryDataSet(dataSet: DataSet): Single<Status> {
		return rxFit.history().insert(dataSet)
				.observeOn(Schedulers.io())
	}

	private fun updateHistoryDataSet(dataUpdateRequest: DataUpdateRequest): Single<Status> {
		return rxFit.history().update(dataUpdateRequest)
				.observeOn(Schedulers.io())
	}

	private fun getWeightDataSet(weight: Float?, date: Long): DataSet {
		return createDataForRequest(DataType.TYPE_WEIGHT, DataSource.TYPE_RAW, weight, date, date, TimeUnit.MILLISECONDS)
	}

	private fun getHeightDataSet(height: Float?, date: Long): DataSet {
		return createDataForRequest(DataType.TYPE_HEIGHT, DataSource.TYPE_RAW, height, date, date, TimeUnit.MILLISECONDS)
	}

	private fun createDataForRequest(dataType: DataType, dataSourceType: Int, value: Float?, startTime: Long, endTime: Long, timeUnit: TimeUnit): DataSet {
		val dataSource = DataSource.Builder()
//				.setAppPackageName(context.getApplicationContext().getPackageName())
				.setDataType(dataType)
				.setType(dataSourceType)
				.build()

		val dataSet = DataSet.create(dataSource)
		val dataPoint = dataSet.createDataPoint()
				.setTimeInterval(startTime, endTime, timeUnit)
				.setFloatValues(value!!)

		dataSet.add(dataPoint)
		return dataSet
	}

	private fun now(): Long {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
	}

	fun updateFitMeasurement(measurementType: MeasurementType, value: Double, value2: Double?, localDateTime: LocalDateTime, unit: String): Single<Status> {

		val date = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

		return when (measurementType) {
			MeasurementType.HEIGHT -> {
				val height: Float
				if (unit == Constant.MeasurementUnit.CM) {
					height = value.toFloat() / 100
				} else {
					height = value.toFloat()
				}
				updateHeight(height, date)
			}
			MeasurementType.WEIGHT -> updateWeight(value.toFloat(), date)
			else -> Single.never()
		}
	}

	fun deleteFromFit(measurementType: MeasurementType, localDateTime: LocalDateTime): Single<Status> {
		val date = localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli()

		return when (measurementType) {
			MeasurementType.HEIGHT -> deleteHeight(date)
			MeasurementType.WEIGHT -> deleteWeight(date)
			else -> Single.never()
		}//			case HEART_RATE:
		//				return deleteHeartrate(date);
		//			case BLOOD_PRESSURE:
		//				return deleteBloodPressure(date);
		//			case BLOOD_SUGAR:
		//				return deleteBloodSugar(date);
	}

	private fun deleteBloodSugar(time: Long): Single<Void> {
		return Single.never()
	}

	private fun deleteBloodPressure(time: Long): Single<Void> {
		return Single.never()
	}

	private fun deleteHeartrate(time: Long): Single<Void> {
		return Single.never()
	}
}