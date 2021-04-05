package ru.endroad.component.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PresenterMviAbstract<STATE, EVENT> : ViewModel() {

	abstract val state: MutableStateFlow<STATE>

	abstract fun reduce(event: EVENT)

	fun STATE.applyState() {
		state.tryEmit(this)
	}
}