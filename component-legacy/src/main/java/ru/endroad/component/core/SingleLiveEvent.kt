package ru.endroad.component.core

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T?>() {

	private val mPending = AtomicBoolean(false)

	@MainThread
	fun subscribe(owner: LifecycleOwner?, observer: (T?) -> Unit) {
		if (hasActiveObservers()) {
			Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
		}

		// Observe the internal MutableLiveData
		super.observe(owner!!, Observer { t ->
			if (mPending.compareAndSet(true, false)) {
				observer(t)
			}
		})
	}

	@MainThread
	override fun setValue(t: T?) {
		mPending.set(true)
		super.setValue(t)
	}

	operator fun invoke(t: T) {
		value = t
	}

	/**
	 * Used for cases where T is Void, to make calls cleaner.
	 */
	@MainThread
	fun call() {
		value = null
	}

	companion object {
		private const val TAG = "SingleLiveEvent"
	}
}