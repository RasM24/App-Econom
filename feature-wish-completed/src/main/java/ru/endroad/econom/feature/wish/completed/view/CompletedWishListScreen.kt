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
			val state = rememberState.value

			when {
				state is CompletedScreenState.Idle          -> IdleScene()

				state is CompletedScreenState.Data
					&& state.completedWishList.isNotEmpty() -> DataScene(state.completedWishList)

				state is CompletedScreenState.Data
					&& state.completedWishList.isEmpty()    -> NoCompletedStubScene()
			}
		}
	}
}