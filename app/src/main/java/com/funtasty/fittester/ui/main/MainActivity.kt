package com.funtasty.fittester.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.funtasty.fittester.App
import com.funtasty.fittester.R
import com.funtasty.fittester.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes
import com.patloew.rxfit.GoogleAPIConnectionException
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {

	@Inject lateinit var presenter: MainPresenter
	private lateinit var apiClient: GoogleApiClient

	private val PERMS_REQUEST_CODE = 2121
	private val RESOLUTION = 2122
	private val REQUEST_OAUTH_REQUEST_CODE = 2123
	private lateinit var signInClient: GoogleSignInClient

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		presenter.attachView(this)

		isPermsGranted()

		val signInOptions = GoogleSignInOptions
				.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.addExtension(getFitnessOptions)
				.build()
		signInClient = GoogleSignIn.getClient(this, signInOptions)
		btnGetData.setOnClickListener {
			googleSignIn()
		}

		btnRevokeAccess.setOnClickListener {
			revokeAccess()
		}
//		val request: DataReadRequest
//		val result = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
//				.readData(request)
	}

	private fun revokeAccess() {

//		Fitness.getConfigClient(this, GoogleSignIn.getLastSignedInAccount(this))
//				.disableFit()
		signInClient.revokeAccess()
				.addOnCompleteListener {
					Timber.d("accessRevoked")
					isPermsGranted()
				}
				.addOnFailureListener {
					Timber.e("accessnot Revoked ${it.message}")

				}
		isPermsGranted()
	}

	private fun googleSignIn() {
		isPermsGranted()
		val bodyPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED
		val locationPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
		if (bodyPerm && locationPerm) {
			presenter.onGetData(true, this)
		} else {
			getPerms()
		}

	}

	private val getFitnessOptions: FitnessOptions = FitnessOptions.builder()
			.addDataType(HealthDataTypes.TYPE_BLOOD_PRESSURE, FitnessOptions.ACCESS_READ)
			.addDataType(HealthDataTypes.TYPE_BLOOD_GLUCOSE, FitnessOptions.ACCESS_READ)
			.addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_WRITE)
			.addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
			.addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE)
			.build()

	private fun isPermsGranted(): Boolean {
		val bodyPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED
		val locationPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
		val fitnessPerm = GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), getFitnessOptions)
		Timber.d("body perm: $bodyPerm \nlocation perm: $locationPerm \nfitness perm: $fitnessPerm")
		return bodyPerm && locationPerm && fitnessPerm
	}


	override fun setStatusText(text: String) {
		this.text.text = text
	}

	override fun getPerms() {
		ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BODY_SENSORS, Manifest.permission.ACCESS_FINE_LOCATION), PERMS_REQUEST_CODE)
	}

	override fun handleResolution(exception: GoogleAPIConnectionException) {
		exception.connectionResult?.startResolutionForResult(this, RESOLUTION)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		when (requestCode) {
			PERMS_REQUEST_CODE -> {
				if (grantResults.isNotEmpty()
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					presenter.onGetData(true, this)
				}
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		when (requestCode) {
			RESOLUTION -> {
				if (resultCode == Activity.RESULT_OK) {
					presenter.onGetData(true, this)
				}
			}
			REQUEST_OAUTH_REQUEST_CODE -> {
				if (resultCode == Activity.RESULT_OK) {
					presenter.onGetData(true, this)
				}
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		presenter.detachView()
	}

	override fun inject() {
		App.applicationComponent
				.plus(MainActivityComponent.MainActivityModule(this))
				.inject(this)
	}


	override fun getActivityLayout(): Int = R.layout.activity_main
}
