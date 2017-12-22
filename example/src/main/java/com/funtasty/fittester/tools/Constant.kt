package com.funtasty.fittester.tools

interface Constant {

	interface MeasurementType {
		companion object {

			val HEIGHT = "height"
			val WEIGHT = "weight"
			val BLOOD_PRESSURE = "bloodPressure"
			val HEART_RATE = "heartRate"
			val CHOLESTEROL = "cholesterol"
			val BLOOD_SUGAR = "bloodSugar"
			val BMI = "bmi"
		}
	}

	interface MeasurementUnit {
		companion object {
			val M = "m"
			val CM = "cm"
			val KG = "kg"
			val MM_HG = "mmHg"
			val BPM = "bpm"
			val MMOL = "mmol/L"
			val NONE = ""
		}
	}
}

