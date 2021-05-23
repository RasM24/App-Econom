package ru.endroad.feature.wish.detail.view

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.composable.NavigationIcon
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishViewPresenter
import ru.endroad.shared.wish.core.entity.Wish

class EditWishScreen(wishId: Int?) : MigrateComposeScreen<EditScreenState>() {

	override val presenter by inject(EditWishViewPresenter::class.java) { parametersOf(wishId) }

	@Deprecated("разобраться с логикой title")
	override val titleRes = R.string.edit_wish_title

	@Composable
	override fun Render(screenState: EditScreenState) {
		Scaffold(topBar = composeFlatTopBar()) {
			when (screenState) {
				EditScreenState.Initial            -> Unit
				is EditScreenState.InitialEditWish -> RenderEditWish(screenState.wish)
				EditScreenState.InitialNewWish     -> RenderWishDetail(createWish = createWishFunction)
			}
		}
	}

	private val createWishFunction = { name: String, info: String, cost: String, importance: String ->
		presenter.saveWish(name = name, info = info, cost = cost, importance = importance)
	}

	@Composable
	private fun RenderEditWish(wish: Wish) {
		RenderWishDetail(
			nameDraft = wish.name,
			infoDraft = wish.info,
			costDraft = wish.cost.toString(),
			importanceDraft = wish.importance.name,
			createWish = createWishFunction
		)
	}

	private fun composeFlatTopBar(): @Composable () -> Unit = {
		TopAppBar(
			title = { Text(text = stringResource(id = titleRes)) },
			navigationIcon = { NavigationIcon(onClick = { presenter.back() }) },
		)
	}
}