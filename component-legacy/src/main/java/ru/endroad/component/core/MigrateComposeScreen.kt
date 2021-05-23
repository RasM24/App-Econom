package ru.endroad.component.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Deprecated("Переформировать базовый класс")
abstract class MigrateComposeScreen<STATE> {

	abstract val presenter: PresenterMviAbstract<STATE>

	@Composable
	protected abstract fun Render(screenState: STATE)

	@Composable
	fun SceneCompose() {
		val state = presenter.state.collectAsState()
		Render(screenState = state.value)
	}
}