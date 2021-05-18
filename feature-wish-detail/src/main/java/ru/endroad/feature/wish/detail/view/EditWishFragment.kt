package ru.endroad.feature.wish.detail.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.endroad.component.core.MigrateComposeScreen
import ru.endroad.component.core.composeFlatTopBar
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenEvent
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishViewPresenter
import ru.endroad.shared.wish.core.entity.Wish

class EditWishFragment : MigrateComposeScreen<EditScreenState, EditScreenEvent>() {

	//region fragment legacy
	companion object {

		private const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().apply {
				wishId?.let { arguments = bundleOf(WISH_ID to wishId) }
			}
	}

	private val wishId: Int? by lazy { arguments?.getInt(WISH_ID) }
	//endregion

	override val presenter by inject<EditWishViewPresenter> { parametersOf(wishId) }

	@Deprecated("разобраться с логикой title")
	override val titleRes = R.string.edit_wish_title

	@Composable
	override fun Render(screenState: EditScreenState) {
		Scaffold(topBar = composeFlatTopBar()) {
			when (screenState) {
				EditScreenState.Initial            -> Unit
				is EditScreenState.InitialEditWish -> RenderEditWish(screenState.wish)
				EditScreenState.InitialNewWish     -> RenderWishDetail(createWish = createWishFunction)
				EditScreenState.WishSaved          -> requireFragmentManager().popBackStack() //TODO fragment legacy
			}
		}
	}

	private val createWishFunction = { name: String, info: String, cost: String, importance: String ->
		presenter.reduce(EditScreenEvent.ApplyClick(name = name, info = info, cost = cost, importance = importance))
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
}