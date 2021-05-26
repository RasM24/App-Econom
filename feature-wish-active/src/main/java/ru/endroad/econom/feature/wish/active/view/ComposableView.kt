package ru.endroad.econom.feature.wish.active.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ru.endroad.shared.wish.core.entity.Wish

@Composable
internal fun WishList(
	wishList: List<Wish>,
	onNewWishClick: () -> Unit,
	onSelectWish: (Wish) -> Unit,
	scaffoldState: ScaffoldState,
) {
	Scaffold(
		floatingActionButton = { AddFloatingActionButton(onClick = onNewWishClick) },
		scaffoldState = scaffoldState,
	) {
		LazyColumn(modifier = Modifier.fillMaxSize()) {
			items(wishList, Wish::id) {
				WishCard(wish = it, onClick = { onSelectWish(it) })
			}
		}
	}
}

@Composable
internal fun LaunchCompletedSnackbar(scaffoldState: ScaffoldState, onAction: () -> Unit, onCloseSnack: () -> Unit) {
	LaunchedEffect(null) {
		val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
			message = "Выполнено",
			actionLabel = "отменить"
		)

		when (snackbarResult) {
			SnackbarResult.Dismissed       -> Unit
			SnackbarResult.ActionPerformed -> onAction()
		}

		onCloseSnack()
	}
}

@Composable
internal fun LaunchDeletedSnackbar(scaffoldState: ScaffoldState, onAction: () -> Unit, onCloseSnack: () -> Unit) {
	LaunchedEffect(null) {
		val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
			message = "Удалено",
			actionLabel = "отменить"
		)

		when (snackbarResult) {
			SnackbarResult.Dismissed       -> Unit
			SnackbarResult.ActionPerformed -> onAction()
		}

		onCloseSnack()
	}
}