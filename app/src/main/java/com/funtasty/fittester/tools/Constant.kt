package com.funtasty.fittester.tools

interface Constant {

	interface Intent {
		companion object {

			val DATE_FROM = "date_from"
			val DATE_TO = "date_to"
			val TIMELINE_ITEM_ID = "timeline_item_id"
			val MEASUREMENT_TYPE = "measurement_type"
			val MEASUREMENT_ID = "measurement_id"
			val MEASUREMENT_SYNC = "measurement_sync"
			val CLOSE_PERSON_ID = "close_person_id"
			val DOCTOR_ID = "doctor_id"
			val MEDICATION_REMINDER_ID = "medication_reminder_id"
			val MEDICATION_HISTORY_ID = "medication_history_id"
			val MEDICAMENT_ID = "medicament_id"
			val DOCTOR_NAME = "doctor_name"
			val TIMELINE_CATEGORY = "timeline_category"
			val TIMELINE_RECORD_ID = "timeline_record_id"
			val TIMELINE_EVENT_DATE_TIME = "timeline_event_date_time"
			val READONLY = "readonly"
			val SHOW_ALL = "show_all"
			val SHOW_HISTORY = "show_history"
			val HISTORICAL_ANAMNESIS_ITEM_ID = "historical_anamnesis_item_id"
			val HISTORICAL_ANAMNESIS_ITEM_TYPE = "historical_anamnesis_item_type"
			val FAMILY_MEMBER_ID = "family_member_id"
			val EXAMINATION_ID = "examination_id"
			val DOCUMENT_ID = "document_id"
			val LABORATORY_REPORT_ID = "laboratory_report_id"
			val VACCINATION_ID = "vaccination_id"
			val VACCINE_CODE = "vaccine_code"
			val REGISTRATION_PAGE = "registration_page"
			val UNAUTHORIZED_ACCESS = "unauthorized_access"
			val LAUNCHER_ONBOARDING_POSITION = "launcher_onboarding_position"
			val NOTE_ID = "note_id"
			val ANAMNESIS_LINK_ID = "link_id"
			val ANAMNESIS_IS_PATIENT = "is_patient"
			val ANAMNESIS_ADD_LINK_ID = "add_link_id"
			val ANAMNESIS_CHILD_INDEX = "child_index"
			val PREVENTION_EXAMINATION_DETAIL_ID = "prevention_examinatin_detail_id"
			val PREVENTION_EXAMINATION_DETAIL_CODE = "prevention_examinatin_detail_code"
			val PREVENTION_EXAMINATION_DETAIL_SCHEDULE_EDIT = "prevention_examinatin_detail_schedule_edit"
			val PREVENTION_PROGRAM_ID = "prevention_program_id"
			val PREVENTION_PROGRAM_DETAIL_ID = "prevention_program_detail_id"
			val DOWNLOAD_FILENAME = "download_filename"
			val DOWNLOAD_URL = "download_url"
			val DOWNLOAD_MIMETYPE = "download_mimetype"
			val EMAIL = "email"
			val INSURANCE_NUMBER = "insurance_number"
			val MONTH = "month"
		}
	}

	interface Package {
		companion object {

			val AMBULANCE = "com.medicalit.zachranka"
		}
	}

	interface PhoneNumber {
		companion object {

			val CALL_CENTER = "+420212288100"
		}
	}

	interface Request {
		companion object {

			val DOCTOR = 0
			val FILE = 1
			val MEDICAMENT = 2
			val ADD_MEASUREMENT = 3
			val CAMERA = 4
			val VACCINE = 5
			val ADD_DOCUMENT = 6
			val EDIT_DOCUMENT = 7
			val MEDICATION = 8
			val CONVERSATION = 9
			val DELETE_PREVENTION = 10
			val DELETE_VACCINATION = 11
			val PIN_AUTHORIZATION = 12
			val ADD_BULK_MEASUREMENTS = 13
		}
	}

	interface Result {
		companion object {

			val RESULT_ADD_EDIT = 100
			val RESULT_DELETE = 101
		}
	}

	interface GsonTypeAdapter {
		companion object {

			val VALUE = "value"
			val PERMISSION = "permission"
		}
	}

	interface Url {
		companion object {

			val CZECH_POINT_MAP = "http://www.czechpoint.cz/wwwstats/f?p=100:16"
			val ZDRAVEL_TWITTER_URL = "https://twitter.com/zdravelcz"
			val ZDRAVEL_TWITTER_APP = "twitter://user?screen_name=zdravelcz"
			val ZDRAVEL_FACEBOOK_URL = "https://www.facebook.com/zdravel/"
			val ZDRAVEL_FACEBOOK_APP = "fb://page/1842469756024770"
			val DOCTOR_SPECIALIZATION = "https://point.vzp.cz/cms/document/download?filename=ciselnikodborn"
			val PACIENT_INSURANCE_ICON = "%sassets/images/insurance/%s.png"
			val TRAVEL_INSURANCE = "https://kalkulacka.csobpoj.cz/cestovni-pojisteni?p.hec=8105162&p.oec=3&z.hec=8105162&z.oec=3"
			val ZDRAVEL_URL = "https://www.zdravel.cz"
			val CECG_URL = "http://www.cecg.cz"
			val INSTITUTE_URL = "https://www.zdravel.cz/institut"
		}
	}

	interface ResponseHeader {
		companion object {

			val LOCATION = "Location"
		}
	}

	interface Anamnesis {
		companion object {

			val SUMARRY_ANAMNESIS = "ZQ0100"
			val PERSONAL_ANAMNESIS = "ZQ0101"
			val MEDICATION_ANAMNESIS = "ZQ0102"
			val ALLERGOLOGY_ANAMNESIS = "ZQ0103"
			val GYNECOLOGICAL_ANAMNESIS = "ZQ0104"
			val ADDICTIVE_SUBSTANCES = "ZQ0107"
			val FAMILY_ANAMNESIS = "ZQ0108"
			val WORK_ANAMNESIS = "ZQ0109"
			val SOCIAL_ANAMNESIS = "ZQ0110"
			val SPORT_ANAMNESIS = "ZQ0111"
			val HISTORICAL_ANAMNESIS = "ZQ0105"
			val HISTORICAL_ANAMNESIS_INJURE = "ZQ010501"
			val HISTORICAL_ANAMNESIS_OPERATION = "ZQ010502"
			val HISTORICAL_ANAMNESIS_ILLNESS = "ZQ010503"
			val FAMILY_ANAMNESIS_FAMILY_MEMBER = "ZQ010801"
			val SPORT_ANAMNESIS_SPORT = "ZQ011101"
		}
	}

	interface AnamnesisGroupFiles {
		companion object {
			val FAMILY_ANAMNESIS = "anamnesis/family_member.json"
			val HISTORICAL_ANAMNESIS = "anamnesis/historical.json"
			val SPORT_ANAMNESIS = "anamnesis/sport.json"
		}
	}

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

	interface Infinity {
		companion object {
			val LOAD_COUNT = 20
		}
	}

	interface Pattern {
		companion object {
			val PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})"
			val PHONE = "\\d{9}"
		}
	}

	interface FilePicker {
		companion object {
			val MAX_SIZE_IN_B = 10000000
			val FILE_SUPPORT_PDF = arrayOf(".pdf")
			val FILE_SUPPORT_DOC = arrayOf(".doc", ".docx")
			val IMAGE_SUPPORT = arrayOf(".jpg", ".png")
		}
	}

	interface InternetState {
		companion object {
			val INITIAL_INTERVAL = 0
			val INTERVAL_IN_MILLIS = 2000
			val TIMEOUT_IN_MILLIS = 2000
			val URL = "http://clients3.google.com/generate_204"
			val PORT = 80
		}

	}
}

