package ru.endroad.component.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment

abstract class MigrateComposeScreen<STATE, EVENT> : Fragment() {

	abstract val titleRes: Int

	abstract val presenter: PresenterMviAbstract<STATE, EVENT>

	@Composable
	abstract fun Render(screenState: STATE)

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			val state = presenter.state.collectAsState()
			Crossfade(targetState = state) {
				Render(screenState = it.value)
			}
		}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		requireActivity().title = getString(titleRes)
	}
}