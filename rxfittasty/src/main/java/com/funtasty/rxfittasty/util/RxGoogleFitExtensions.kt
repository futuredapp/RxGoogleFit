package com.funtasty.rxfittasty.util

import android.util.Log
import rx.SingleSubscriber

internal inline fun <T> SingleSubscriber<T>.onSafeSuccess(t:T?) {
	if (!this.isUnsubscribed) {
		this.onSuccess(t)
	} else {
		Log.e("RxGoogleFit", "Subscriber is already unsubscribed, onSuccess not called.")
	}
}

internal inline fun <T> SingleSubscriber<T>.onSafeError(error: Throwable?) {
	if (!this.isUnsubscribed) {
		this.onError(error)
	} else {
		Log.e("RxGoogleFit", "Subscriber is already unsubscribed, onError not called.")
	}
}
