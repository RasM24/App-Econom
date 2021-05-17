package ru.endroad.econom.feature.wishes.view

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel
import ru.endroad.shared.wish.core.entity.Wish

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListFragment : MigrateComposeScreen<ListScreenState, ListScreenEvent>() {

	override val presenter by viewModel<WishListViewModel>()

	override val titleRes = R.string.wish_list_title

	@Composable
	override fun Render(screenState: ListScreenState) {
		when (screenState) {
			ListScreenState.Init         -> Unit
			ListScreenState.NoDesire     -> RenderNoDesireStub(doTheMainAction = { presenter.reduce(ListScreenEvent.NewWishClick) })
			ListScreenState.AllCompleted -> RenderAllCompletedStub(
				doTheMainAction = { presenter.reduce(ListScreenEvent.NewWishClick) },
				doTheSecondaryAction = { presenter.reduce(ListScreenEvent.MenuCompletedClick) })
			is ListScreenState.ShowData  -> RenderDataScene(state = screenState)
		}

		//TODO fragment legacy
		val hasWishes = screenState is ListScreenState.ShowData
		setHasOptionsMenu(hasWishes)
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

	//region fragment legacy
	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.wish_list_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menu_completed) presenter.reduce(ListScreenEvent.MenuCompletedClick)
		return true
	}
	//endregion
}