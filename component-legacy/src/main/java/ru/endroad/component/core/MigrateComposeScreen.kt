package ru.endroad.component.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.endroad.compose.core.ComposeScreen

@Deprecated("Переформировать базовый класс")
abstract class MigrateComposeScreen<STATE> : ComposeScreen {

	abstract val presenter: PresenterMviAbstract<STATE>

	@Composable
	protected abstract fun Render(screenState: STATE)

	@Composable
	override fun SceneCompose() {
		val state = presenter.state.collectAsState()
		Render(screenState = state.value)
	}
}