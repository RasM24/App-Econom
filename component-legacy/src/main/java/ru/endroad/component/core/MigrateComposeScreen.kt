package ru.endroad.component.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

abstract class MigrateComposeScreen<STATE, EVENT> {

	abstract val titleRes: Int

	abstract val presenter: PresenterMviAbstract<STATE, EVENT>

	@Composable
	protected abstract fun Render(screenState: STATE)

	@Composable
	fun SceneCompose() {
		val state = presenter.state.collectAsState()
		Render(screenState = state.value)
	}
}