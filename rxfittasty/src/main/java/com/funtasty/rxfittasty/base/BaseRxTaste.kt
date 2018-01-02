package com.funtasty.rxfittasty.base

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import java.util.*

internal abstract class BaseRxTaste(val context: Context) {

	companion object {
		val observableSet: MutableSet<BaseRxTaste> = HashSet()

		fun onResolutionResult(resultCode: Int) {
			for (observable in observableSet) {
				observable.handleResolutionResult(resultCode)
			}
			observableSet.clear()
		}
	}

	fun createGoogleSignInClient(fitnessOptions: FitnessOptions): GoogleSignInClient = GoogleSignIn.getClient(context, getSignInOptions(fitnessOptions))

	private fun getSignInOptions(fitnessOptions: FitnessOptions): GoogleSignInOptions {
		return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.addExtension(fitnessOptions)
				.build()

	}

	protected abstract fun handleResolutionResult(resultCode: Int)
}