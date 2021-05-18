package ru.endroad.component.core

import android.view.View
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import ru.endroad.compose.theme.ApplicationTheme

fun Fragment.setComposeView(content: @Composable () -> Unit): View = ComposeView(requireContext()).apply {
	setContent {
		ApplicationTheme {
			content()
		}
	}
}

fun <S, E> MigrateComposeScreen<S, E>.composeFlatTopBar(
	actions: @Composable RowScope.() -> Unit = {},
): @Composable () -> Unit = {
	TopAppBar(
		title = { Text(text = stringResource(id = titleRes)) },
		navigationIcon = composeNavigationIcon(onNavigationClick = { finish() }).takeIf { hasBackStack },
		actions = actions,
	)
}

@Composable
fun composeNavigationIcon(onNavigationClick: () -> Unit): @Composable () -> Unit = {
	IconButton(onClick = onNavigationClick) {
		Icon(
			imageVector = Icons.Filled.ArrowBack,
			contentDescription = "Navigation"
		)
	}
}

val Fragment.hasBackStack: Boolean get() = requireActivity().supportFragmentManager.backStackEntryCount > 0