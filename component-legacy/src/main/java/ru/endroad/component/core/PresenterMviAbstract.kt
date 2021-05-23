package ru.endroad.component.core

import kotlinx.coroutines.flow.MutableStateFlow

@Deprecated("Переформировать базовый класс")
abstract class PresenterMviAbstract<STATE> {

	abstract val state: MutableStateFlow<STATE>

	fun STATE.applyState() {
		state.tryEmit(this)
	}
}