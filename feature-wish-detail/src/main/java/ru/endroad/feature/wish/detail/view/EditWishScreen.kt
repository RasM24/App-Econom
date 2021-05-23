package ru.endroad.feature.wish.detail.view

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.composable.IdleScreen
import ru.endroad.composable.NavigationIcon
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishViewPresenter

class EditWishScreen(wishId: Int?) : MigrateComposeScreen<EditScreenState>() {

	override val presenter by inject(EditWishViewPresenter::class.java) { parametersOf(wishId) }

	@Composable
	override fun Render(screenState: EditScreenState) {
		Scaffold(topBar = composeFlatTopBar()) {
			when (screenState) {
				EditScreenState.Idle -> IdleScreen()
				is EditScreenState.DraftWish -> RenderWishDetail(
					nameDraft = screenState.name,
					infoDraft = screenState.info,
					costDraft = screenState.cost?.toString(),
					importanceDraft = screenState.importance?.name,
					createWish = { name, info, cost, importance ->
						presenter.saveWish(name = name, info = info, cost = cost, importance = importance)
					}
				)
			}
		}
	}

	@Deprecated("разобраться с логикой title")
	private fun composeFlatTopBar(): @Composable () -> Unit = {
		TopAppBar(
			title = { Text(text = stringResource(id = R.string.edit_wish_title)) },
			navigationIcon = { NavigationIcon(onClick = { presenter.back() }) },
		)
	}
}