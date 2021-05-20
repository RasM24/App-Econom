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
import ru.endroad.component.core.composeFlatTopBar
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewPresenter
import ru.endroad.shared.wish.core.entity.Wish

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListScreen : MigrateComposeScreen<ListScreenState, ListScreenEvent>() {

	override val presenter by inject(WishListViewPresenter::class.java)

	override val titleRes = R.string.wish_list_title

	@Composable
	override fun Render(screenState: ListScreenState) {
		//TODO забыл использовать этот флаг
		val hasWishes = screenState is ListScreenState.ShowData

		Scaffold(
			topBar = composeFlatTopBar(actions = composeActions())
		) {
			when (screenState) {
				ListScreenState.Init         -> Unit
				ListScreenState.NoDesire     -> RenderNoDesireStub(doTheMainAction = { presenter.reduce(ListScreenEvent.NewWishClick) })
				ListScreenState.AllCompleted -> RenderAllCompletedStub(
					doTheMainAction = { presenter.reduce(ListScreenEvent.NewWishClick) },
					doTheSecondaryAction = { presenter.reduce(ListScreenEvent.MenuCompletedClick) })
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
																			onAction = { presenter.reduce(ListScreenEvent.UndoPerformClick(action.wish)) },
																			onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			is ListScreenSingleEvent.DeleteWish  -> LaunchDeletedSnackbar(scaffoldState,
																		  onAction = { presenter.reduce(ListScreenEvent.UndoDeleteClick(action.wish)) },
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
						presenter.reduce(ListScreenEvent.EditClick(selectedWish))
						scope.launch { bottomSheetState.hide() }
					},
					onClickComplete = {
						presenter.reduce(ListScreenEvent.PerformClick(selectedWish))
						lastAction = ListScreenSingleEvent.PerformWish(selectedWish)
						scope.launch { bottomSheetState.hide() }
					},
					onClickDelete = {
						presenter.reduce(ListScreenEvent.DeleteClick(selectedWish))
						lastAction = ListScreenSingleEvent.DeleteWish(selectedWish)
						scope.launch { bottomSheetState.hide() }
					},
				)
			},
			//TODO вынести Scaffold наверх
			content = {
				WishList(
					wishList = state.wishList,
					onNewWishClick = { presenter.reduce(ListScreenEvent.NewWishClick) },
					onSelectWish = {
						selectedWish = it
						scope.launch { bottomSheetState.show() }
					},
					scaffoldState = scaffoldState
				)
			}
		)
	}

	private fun composeActions(): @Composable RowScope.() -> Unit = {
		CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
			IconButton(onClick = { presenter.reduce(ListScreenEvent.MenuCompletedClick) }) {
				Icon(
					imageVector = Icons.Outlined.Task,
					contentDescription = stringResource(R.string.wish_list_menu_completed)
				)
			}
		}
	}
}