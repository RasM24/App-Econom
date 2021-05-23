package ru.endroad.econom.feature.wishes.view

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.composable.IdleScreen
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewPresenter
import ru.endroad.shared.wish.core.entity.Wish

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListScreen : MigrateComposeScreen<ListScreenState>() {

	override val presenter by inject(WishListViewPresenter::class.java)

	@Composable
	override fun Render(screenState: ListScreenState) {
		//TODO забыл использовать этот флаг
		val hasWishes = screenState is ListScreenState.ShowData

		Scaffold(
			topBar = composeFlatTopBar(actions = composeActions(hasWishes))
		) {
			when (screenState) {
				ListScreenState.Idle         -> IdleScreen()
				ListScreenState.NoDesire     -> RenderNoDesireStub(doTheMainAction = { presenter.openNewWishScreen() })
				ListScreenState.AllCompleted -> RenderAllCompletedStub(
					doTheMainAction = { presenter.openNewWishScreen() },
					doTheSecondaryAction = { presenter.openCompletedWishScreen() })
				is ListScreenState.ShowData  -> RenderDataScene(state = screenState)
			}
		}
	}

	//TODO Много говнокода. Изучить детальнее compose и навести здесь порядок
	@OptIn(ExperimentalMaterialApi::class)
	@Composable
	private fun RenderDataScene(state: ListScreenState.ShowData) {
		val scaffoldState = rememberScaffoldState()

		//TODO придумать, как работать с BottomSheet и selectable entity
		var selectedWish: Wish by remember { mutableStateOf(Wish(name = "mock", cost = 0)) }
		var lastAction: ListScreenSingleEvent by remember { mutableStateOf(ListScreenSingleEvent.Nothing) }

		when (val action = lastAction) {
			is ListScreenSingleEvent.PerformWish -> LaunchCompletedSnackbar(scaffoldState,
																			onAction = { presenter.undoPerformWish(action.wish) },
																			onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			is ListScreenSingleEvent.DeleteWish  -> LaunchDeletedSnackbar(scaffoldState,
																		  onAction = { presenter.undoDeleteWish(action.wish) },
																		  onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			ListScreenSingleEvent.Nothing        -> Unit
		}

		val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
		val scope = rememberCoroutineScope()

		ModalBottomSheetLayout(
			sheetState = bottomSheetState,
			sheetContent = {
				WishActionBottomSheet(
					title = selectedWish.name,
					onClickEdit = {
						presenter.openEditWishScreen(selectedWish.id)
						scope.launch { bottomSheetState.hide() }
					},
					onClickComplete = {
						presenter.perform(selectedWish)
						lastAction = ListScreenSingleEvent.PerformWish(selectedWish)
						scope.launch { bottomSheetState.hide() }
					},
					onClickDelete = {
						presenter.delete(selectedWish)
						lastAction = ListScreenSingleEvent.DeleteWish(selectedWish)
						scope.launch { bottomSheetState.hide() }
					},
				)
			},
			//TODO вынести Scaffold наверх
			content = {
				WishList(
					wishList = state.wishList,
					onNewWishClick = { presenter.openNewWishScreen() },
					onSelectWish = {
						selectedWish = it
						scope.launch { bottomSheetState.show() }
					},
					scaffoldState = scaffoldState
				)
			}
		)
	}

	private fun composeActions(hasWishes: Boolean): @Composable RowScope.() -> Unit = {
		CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
			if (hasWishes) {
				IconButton(onClick = { presenter.openCompletedWishScreen() }) {
					Icon(
						imageVector = Icons.Outlined.Task,
						contentDescription = stringResource(R.string.wish_list_menu_completed)
					)
				}
			}
		}
	}

	private fun composeFlatTopBar(actions: @Composable RowScope.() -> Unit = {}): @Composable () -> Unit = {
		TopAppBar(
			title = { Text(text = stringResource(id = R.string.wish_list_title)) },
			actions = actions,
		)
	}
}