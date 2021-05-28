package ru.endroad.feature.wish.detail.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.endroad.composable.IdleScene
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishActor

class EditWishScreen(private val wishId: Int?) : ComposeScreen {

	private val actor = EditWishActor(wishId)

	init {
		CoroutineScope(Dispatchers.Main).launch { actor.loadData() }
	}

	@Composable
	override fun SceneCompose() {
		val rememberState = actor.state.collectAsState()
		val screenState = rememberState.value
		val coroutineScope = rememberCoroutineScope()

		Scaffold(
			topBar = {
				FlatTopBar(title = stringResource(id = R.string.edit_wish_title), navigationClick = actor::close)
			},
			content = {
				when (screenState) {
					EditScreenState.Idle -> IdleScene()

					is EditScreenState.DraftWish -> RenderWishDetailScene(
						nameDraft = screenState.name,
						infoDraft = screenState.info,
						costDraft = screenState.cost?.toString(),
						importanceDraft = screenState.importance?.name,
						createWish = { name, info, cost, importance ->
							coroutineScope.launch { actor.saveWish(name = name, info = info, cost = cost, importance = importance) }
						}
					)
				}
			},
		)
	}
}