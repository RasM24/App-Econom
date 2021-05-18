package ru.endroad.econom.feature.wishes.completed.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.component.core.composeFlatTopBar
import ru.endroad.econom.feature.wishes.completed.R
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishesFragment : MigrateComposeScreen<CompletedScreenState, CompletedScreenEvent>() {

	override val presenter by viewModel<CompletedWishListViewModel>()

	override val titleRes = R.string.completed_list_title

	@Composable
	override fun Render(screenState: CompletedScreenState) {
		Scaffold(topBar = composeFlatTopBar()) {
			when (screenState) {
				is CompletedScreenState.ShowData -> RenderData(screenState)
				else                             -> Unit
			}
		}
	}

	@Composable
	private fun RenderData(state: CompletedScreenState.ShowData) {
		if (state.completedWishList.isEmpty()) {
			RenderNoCompletedStub()
		} else {
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.completedWishList, Wish::id) { WishItem(wish = it) }
			}
		}
	}
}