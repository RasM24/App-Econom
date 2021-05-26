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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.composable.IdleScene
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.econom.feature.wish.active.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wish.active.entity.ListScreenState
import ru.endroad.econom.feature.wish.active.presenter.WishFlowRouting
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.domain.PerformWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

//TODO Много говнокода. Изучить детальнее compose и навести здесь порядок
class ActiveWishListScreen : ComposeScreen {

	private val addWishUseCase by inject(AddWishUseCase::class.java)
	private val deleteWishUseCase by inject(DeleteWishUseCase::class.java)
	private val performWishUseCase by inject(PerformWishUseCase::class.java)
	private val getWishListUseCase by inject(GetWishListUseCase::class.java)
	private val router by inject(WishFlowRouting::class.java)

	private val message = MutableSharedFlow<ListScreenSingleEvent?>()

	private val state = MutableStateFlow<ListScreenState>(ListScreenState.Idle)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishListUseCase().collect { wishList ->
				ListScreenState.Data(wishList = wishList.filterNot(Wish::complete).reversed(), hasCompletedWish = wishList.any(Wish::complete))
					.let { state.emit(it) }
			}
		}
	}

	private fun perform(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch {
			performWishUseCase(wish)
			message.emit(ListScreenSingleEvent.PerformWish(wish))
		}
	}

	private fun delete(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch {
			deleteWishUseCase(wish)
			message.emit(ListScreenSingleEvent.DeleteWish(wish))
		}
	}

	private fun undoDeleteWish(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch { addWishUseCase(wish) }
	}

	private fun undoPerformWish(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch { performWishUseCase(wish, complete = false) }
	}

	@Composable
	override fun SceneCompose() {
		val rememberState = state.collectAsState()
		val screenState = rememberState.value

		val hasWishes = (screenState as? ListScreenState.Data)?.wishList?.isNotEmpty() ?: false

		Scaffold(
			topBar = {
				FlatTopBar(actions = {
					if (hasWishes) MenuCompletedTaskActions(onClick = router::openCompletedWishScreen)
				})
			},
			content = {
				when (screenState) {
					ListScreenState.Idle -> IdleScene()
					is ListScreenState.Data -> RenderSelector(screenState)
				}
			}
		)
	}

	@Composable
	private fun RenderSelector(state: ListScreenState.Data) = when {
		state.wishList.isNotEmpty() -> RenderDataScene(state.wishList)
		!state.hasCompletedWish     -> NoDesireStubScene(doTheMainAction = router::openNewWishScreen)
		else                        -> AllCompletedStubScene(
			doTheMainAction = router::openNewWishScreen,
			doTheSecondaryAction = router::openCompletedWishScreen
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
																			onAction = { undoPerformWish(action.wish) },
																			onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			is ListScreenSingleEvent.DeleteWish  -> LaunchDeletedSnackbar(scaffoldState,
																		  onAction = { undoDeleteWish(action.wish) },
																		  onCloseSnack = { lastAction = ListScreenSingleEvent.Nothing }
			)
			ListScreenSingleEvent.Nothing        -> Unit
		}

		ModalBottomSheetLayout(
			sheetState = bottomSheetState,
			sheetContent = {
				ExperimentalWishActionBottomSheet(
					title = selectedWish.name,
					onClickEdit = { router.openEditWishScreen(selectedWish.id) },
					onClickComplete = {
						perform(selectedWish)
						lastAction = ListScreenSingleEvent.PerformWish(selectedWish)
					},
					onClickDelete = {
						delete(selectedWish)
						lastAction = ListScreenSingleEvent.DeleteWish(selectedWish)
					},
					bottomSheetState = bottomSheetState,
					coroutineScope = scope,
				)
			},
			//TODO вынести Scaffold наверх
			content = {
				Scaffold(
					floatingActionButton = { AddFloatingActionButton(onClick = router::openNewWishScreen) },
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