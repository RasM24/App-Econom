package ru.endroad.component.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface Navigator {

	val container: Int
	var hubActivity: AppCompatActivity?

	fun open(fragment: Fragment) {
		hubActivity?.supportFragmentManager?.forwardTo(fragment, defaultForwardAnimation, container)
	}

	fun changeRoot(fragment: Fragment) {
		hubActivity?.supportFragmentManager?.changeRoot(fragment, defaultReplaceAnimation, container)
	}
}