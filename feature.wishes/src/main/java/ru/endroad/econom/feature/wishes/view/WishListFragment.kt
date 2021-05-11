package ru.endroad.econom.feature.wishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.component.core.setComposeView
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel
import ru.endroad.shared.wish.core.entity.Wish

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListFragment : Fragment() {

	private val presenter by viewModel<WishListViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			val state = presenter.state.collectAsState()

			Crossfade(targetState = state) {
				when (val screenState = it.value) {
					ListScreenState.Init        -> Unit
					ListScreenState.NoData      -> Unit
					is ListScreenState.ShowData -> RenderDataScene(state = screenState)
				}
			}
		}

	@Composable
	private fun RenderDataScene(state: ListScreenState.ShowData) {
		val scaffoldState = rememberScaffoldState()
		val singleEvent = presenter.message.collectAsState(null)

		when (val event = singleEvent.value) {
			is ListScreenSingleEvent.PerformWish -> LaunchCompletedSnackbar(scaffoldState) { presenter.reduce(ListScreenEvent.UndoPerformClick(event.wish)) }
			is ListScreenSingleEvent.DeleteWish  -> LaunchDeletedSnackbar(scaffoldState) { presenter.reduce(ListScreenEvent.UndoDeleteClick(event.wish)) }
			else                                 -> Unit
		}

		Scaffold(
			floatingActionButton = newWishFab { presenter.reduce(ListScreenEvent.NewWishClick) },
			scaffoldState = scaffoldState,
		) {
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.wishList, Wish::id) { WishItem(wish = it, onClick = { showBottomSheet(it) }) }
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.wish_list_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun showBottomSheet(wish: Wish) {
		showBottomSheetActionWish(
			wish.name,
			onClickCompleteListener = { presenter.reduce(ListScreenEvent.PerformClick(wish)) },
			onClickEditListener = { presenter.reduce(ListScreenEvent.EditClick(wish)) },
			onClickDeleteListener = { presenter.reduce(ListScreenEvent.DeleteClick(wish)) }
		)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menu_completed) presenter.reduce(ListScreenEvent.MenuCompletedClick)
		return true
	}

	companion object {

		fun getInstance(): Fragment =
			WishListFragment()
	}
}