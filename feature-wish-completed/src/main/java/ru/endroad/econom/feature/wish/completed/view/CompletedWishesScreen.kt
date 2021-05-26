package ru.endroad.econom.feature.wish.completed.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.composable.IdleScreen
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.econom.feature.wish.completed.presenter.CompletedScreenState
import ru.endroad.econom.feature.wish.completed.presenter.WishCompletedListRouter
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishesScreen : ComposeScreen {

	private val router by inject(WishCompletedListRouter::class.java)
	private val getWishListUseCase by inject(GetWishListUseCase::class.java)

	private val state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Idle)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishListUseCase().collect { wishList ->
				val completedWishes = wishList.filter(Wish::complete)
				state.emit(CompletedScreenState.Data(completedWishes))
			}
		}
	}

	@Composable
	override fun SceneCompose() {
		Scaffold(topBar = { FlatTopBar(navigationClick = router::close) }) {
			val rememberState = state.collectAsState()

			when (val screenState = rememberState.value) {
				CompletedScreenState.Idle    -> IdleScreen()
				is CompletedScreenState.Data -> RenderData(screenState)
			}
		}
	}

	@Composable
	private fun RenderData(state: CompletedScreenState.Data) {
		if (state.completedWishList.isEmpty()) {
			NoCompletedStubScene()
		} else {
			DataScene(wishList = state.completedWishList)
		}
	}
}