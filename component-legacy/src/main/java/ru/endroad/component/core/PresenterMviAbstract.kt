package ru.endroad.component.core

import kotlinx.coroutines.flow.MutableStateFlow

abstract class PresenterMviAbstract<STATE, EVENT> {

	abstract val state: MutableStateFlow<STATE>

	abstract fun reduce(event: EVENT)

	fun STATE.applyState() {
		state.tryEmit(this)
	}
}