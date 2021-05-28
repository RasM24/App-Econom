package ru.endroad.econom.feature.wish.active.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.endroad.composable.IdleScene
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.econom.feature.wish.active.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wish.active.entity.ListScreenState
import ru.endroad.econom.feature.wish.active.presenter.ActiveWishListActor
import ru.endroad.shared.wish.core.entity.Wish

//TODO Много говнокода. Изучить детальнее compose и навести здесь порядок
class ActiveWishListScreen : ComposeScreen {

	private val actor = ActiveWishListActor()

	init {
		CoroutineScope(Dispatchers.Main).launch { actor.fetchData() }
	}

	@Composable
	override fun SceneCompose() {
		val rememberState = actor.state.collectAsState()
		val screenState = rememberState.value

		val hasWishes = (screenState as? ListScreenState.Data)?.wishList?.isNotEmpty() ?: false

		Scaffold(
			topBar = {
				FlatTopBar(actions = {
					if (hasWishes) MenuCompletedTaskActions(onClick = actor::openCompletedWishScreen)
				})
			},
			content = {
				when (screenState) {
					ListScreenState.Idle    -> IdleScene()
					is ListScreenState.Data -> RenderSelector(screenState)
				}
			}
		)
	}

	@Composable
	private fun RenderSelector(state: ListScreenState.Data) = when {
		state.wishList.isNotEmpty() -> RenderDataScene(state.wishList)
		!state.hasCompletedWish     -> NoDesireStubScene(doTheMainAction = actor::openNewWishScreen)
		else                        -> AllCompletedStubScene(
			doTheMainAction = actor::openNewWishScreen,
			doTheSecondaryAction = actor::openCompletedWishScreen
		)
	}

	@OptIn(ExperimentalMaterialApi::class)
	@Composable
	private fun RenderDataScene(wishList: List<Wish>) {
		val scaffoldState = rememberScaffoldState()
		val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
		val scope = rememberCoroutineScope()

		//TODO придумать, как работать с BottomSheet и selectable entity
		var selectedWish: Wish by remember { mutableStateOf(Wish(name = "mock", cost = 0)) }
		var lastAction: ListScreenSingleEvent by remember { mutableStateOf(ListScreenSingleEvent.Nothing) }

		when (val action = lastAction) {
			is ListScreenSingleEvent.PerformWish -> LaunchCompletedSnackbar(scaffoldState,
																			onAction = { scope.launch { actor.undoPerformWish(action.wish) } },
																			onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			is ListScreenSingleEvent.DeleteWish  -> LaunchDeletedSnackbar(scaffoldState,
																		  onAction = { scope.launch { actor.undoDeleteWish(action.wish) } },
																		  onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			ListScreenSingleEvent.Nothing        -> Unit
		}

		ModalBottomSheetLayout(
			sheetState = bottomSheetState,
			sheetContent = {
				ExperimentalWishActionBottomSheet(
					title = selectedWish.name,
					onClickEdit = { actor.openEditWishScreen(selectedWish.id) },
					onClickComplete = {
						scope.launch { actor.performWish(selectedWish) }
						lastAction = ListScreenSingleEvent.PerformWish(selectedWish)
					},
					onClickDelete = {
						scope.launch { actor.deleteWish(selectedWish) }
						lastAction = ListScreenSingleEvent.DeleteWish(selectedWish)
					},
					bottomSheetState = bottomSheetState,
					coroutineScope = scope,
				)
			},
			//TODO вынести Scaffold наверх
			content = {
				Scaffold(
					floatingActionButton = { AddFloatingActionButton(onClick = actor::openNewWishScreen) },
					scaffoldState = scaffoldState,
					content = {
						WishList(
							wishList = wishList,
							onSelectWish = {
								selectedWish = it
								scope.launch { bottomSheetState.show() }
							},
						)
					}
				)
			}
		)
	}
}