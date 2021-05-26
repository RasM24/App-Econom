package ru.endroad.econom.feature.wish.completed.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.endroad.composable.IdleScene
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.econom.feature.wish.completed.presenter.CompletedScreenState
import ru.endroad.econom.feature.wish.completed.presenter.CompletedWishListActor

class CompletedWishListScreen : ComposeScreen {

	private val actor = CompletedWishListActor()
		.apply { loadData() }

	@Composable
	override fun SceneCompose() {
		Scaffold(topBar = { FlatTopBar(navigationClick = actor::back) }) {
			val rememberState = actor.state.collectAsState()

			when (val screenState = rememberState.value) {
				CompletedScreenState.Idle    -> IdleScene()
				is CompletedScreenState.Data -> DataScene(screenState)
			}
		}
	}

	@Composable
	private fun DataScene(state: CompletedScreenState.Data) = when {
		state.completedWishList.isNotEmpty() -> DataScene(wishList = state.completedWishList)
		else                                 -> NoCompletedStubScene()
	}
}