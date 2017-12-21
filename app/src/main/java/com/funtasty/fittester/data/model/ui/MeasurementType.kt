package com.funtasty.fittester.data.model.ui

import android.support.annotation.StringRes
import com.funtasty.fittester.R
import com.funtasty.fittester.tools.Constant


enum class MeasurementType constructor(val id: Int,
									   val namet: String,
									   @param:StringRes @get:StringRes val title: Int,
									   val unit: String,
									   val max: Int,
									   val min: Int) {

	HEIGHT(0, Constant.MeasurementType.HEIGHT, R.string.measurement_type_height, Constant.MeasurementUnit.CM, 300, 0),
	WEIGHT(1, Constant.MeasurementType.WEIGHT, R.string.measurement_type_weight, Constant.MeasurementUnit.KG, 500, 0),
	BLOOD_PRESSURE(2, Constant.MeasurementType.BLOOD_PRESSURE, R.string.measurement_type_blood_pressure, Constant.MeasurementUnit.MM_HG, 300, 0),
	HEART_RATE(3, Constant.MeasurementType.HEART_RATE, R.string.measurement_type_heart_rate, Constant.MeasurementUnit.BPM, 300, 0),
	CHOLESTEROL(4, Constant.MeasurementType.CHOLESTEROL, R.string.measurement_type_cholesterol, Constant.MeasurementUnit.MMOL, 100, 0),
	BLOOD_SUGAR(5, Constant.MeasurementType.BLOOD_SUGAR, R.string.measurement_type_blood_sugar, Constant.MeasurementUnit.MMOL, 100, 0),
	BMI(6, Constant.MeasurementType.BMI, R.string.measurement_type_bmi, Constant.MeasurementUnit.NONE, 200, 0);


	override fun toString(): String {
		return namet
	}
}

