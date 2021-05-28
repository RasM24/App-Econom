package ru.endroad.feature.wish.detail.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.composable.IdleScene
import ru.endroad.compose.core.ComposeScreen
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.WishDetailRouter
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

class EditWishScreen(private val wishId: Int?) : ComposeScreen {

	private val getWishUseCase by inject(GetWishUseCase::class.java)
	private val addWishUseCase by inject(AddWishUseCase::class.java)
	private val editWishUseCase by inject(EditWishUseCase::class.java)
	private val router by inject(WishDetailRouter::class.java)

	private val state: MutableStateFlow<EditScreenState> = MutableStateFlow(EditScreenState.Idle)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			val wish = wishId?.let { getWishUseCase(it) }

			EditScreenState.DraftWish(
				name = wish?.name,
				cost = wish?.cost,
				importance = wish?.importance,
				info = wish?.info,
			)
				.let { state.emit(it) }
		}
	}

	private fun saveWish(name: String, cost: String, importance: String, info: String) {
		CoroutineScope(Dispatchers.Main).launch {
			val wish = Wish(name = name, info = info, cost = cost.toInt(), importance = Importance.valueOf(importance))

			wishId?.let { editWishUseCase(wish.copy(id = it)) } ?: addWishUseCase(wish)
			router.close()
		}
	}

	@Composable
	override fun SceneCompose() {
		val rememberState = state.collectAsState()
		val screenState = rememberState.value

		Scaffold(
			topBar = {
				FlatTopBar(title = stringResource(id = R.string.edit_wish_title), navigationClick = router::close)
			},
			content = {
				when (screenState) {
					EditScreenState.Idle -> IdleScene()
					is EditScreenState.DraftWish -> RenderWishDetailScene(
						nameDraft = screenState.name,
						infoDraft = screenState.info,
						costDraft = screenState.cost?.toString(),
						importanceDraft = screenState.importance?.name,
						createWish = { name, info, cost, importance -> saveWish(name = name, info = info, cost = cost, importance = importance) }
					)
				}
			},
		)
	}
}