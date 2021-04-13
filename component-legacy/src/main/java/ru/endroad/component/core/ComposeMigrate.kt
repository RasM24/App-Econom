package ru.endroad.component.core

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.endroad.compose.theme.ApplicationTheme

fun Fragment.setComposeView(content: @Composable () -> Unit): View = ComposeView(requireContext()).apply {
	setContent {
		ApplicationTheme {
			content()
		}
	}
}