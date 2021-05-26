package ru.endroad.econom.feature.wish.completed.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.composable.IdleScreen
import ru.endroad.composable.NavigationIcon
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.econom.feature.wish.completed.R
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
		Scaffold(topBar = { FlatTopBar() }) {
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
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.completedWishList, Wish::id) { WishItem(wish = it) }
			}
		}
	}

	@Composable
	private fun FlatTopBar() = TopAppBar(
		title = { Text(text = stringResource(id = R.string.completed_list_title)) },
		navigationIcon = { NavigationIcon(onClick = { router.close() }) },
	)
}