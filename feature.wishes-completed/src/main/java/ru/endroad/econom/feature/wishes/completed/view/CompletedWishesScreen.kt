package ru.endroad.econom.feature.wishes.completed.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.composable.IdleScreen
import ru.endroad.composable.NavigationIcon
import ru.endroad.econom.feature.wishes.completed.R
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedScreenState
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListPresenter
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishesScreen : MigrateComposeScreen<CompletedScreenState>() {

	override val presenter by inject(CompletedWishListPresenter::class.java)

	override val titleRes = R.string.completed_list_title

	@Composable
	override fun Render(screenState: CompletedScreenState) {
		Scaffold(topBar = { FlatTopBar() }) {
			when (screenState) {
				CompletedScreenState.Idle    -> IdleScreen()
				is CompletedScreenState.Data -> RenderData(screenState)
			}
		}
	}

	@Composable
	private fun RenderData(state: CompletedScreenState.Data) {
		if (state.completedWishList.isEmpty()) {
			NoCompletedStub()
		} else {
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.completedWishList, Wish::id) { WishItem(wish = it) }
			}
		}
	}

	@Composable
	private fun FlatTopBar() = TopAppBar(
		title = { Text(text = stringResource(id = titleRes)) },
		navigationIcon = { NavigationIcon(onClick = { presenter.back() }) },
	)
}