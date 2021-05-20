package ru.endroad.component.core

import kotlinx.coroutines.flow.MutableStateFlow

@Deprecated("Переформировать базовый класс")
abstract class PresenterMviAbstract<STATE, EVENT> {

	abstract val state: MutableStateFlow<STATE>

	abstract fun reduce(event: EVENT)

	fun STATE.applyState() {
		state.tryEmit(this)
	}
}