package ru.endroad.econom.feature.wishes.view

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

internal fun newWishFab(onClick: () -> Unit): @Composable () -> Unit = {
	FloatingActionButton(onClick = onClick) {
		Icon(imageVector = Icons.Default.Add, contentDescription = "Add Wish")
	}
}

@Composable
internal fun LaunchCompletedSnackbar(scaffoldState: ScaffoldState, onAction: () -> Unit) {
	LaunchedEffect(scaffoldState) {
		val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
			message = "Выполнено",
			actionLabel = "отменить"
		)

		when (snackbarResult) {
			SnackbarResult.Dismissed       -> Unit
			SnackbarResult.ActionPerformed -> onAction()
		}
	}
}

@Composable
internal fun LaunchDeletedSnackbar(scaffoldState: ScaffoldState, onAction: () -> Unit) {
	LaunchedEffect(scaffoldState) {
		val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
			message = "Удалено",
			actionLabel = "отменить"
		)

		when (snackbarResult) {
			SnackbarResult.Dismissed       -> Unit
			SnackbarResult.ActionPerformed -> onAction()
		}
	}
}