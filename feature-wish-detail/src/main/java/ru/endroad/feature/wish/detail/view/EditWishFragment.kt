package ru.endroad.feature.wish.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.endroad.component.core.setComposeView
import ru.endroad.feature.wish.detail.presentation.EditScreenEvent
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishViewModel
import ru.endroad.shared.wish.core.entity.Wish

class EditWishFragment : Fragment() {

	private val wishId: Int? by lazy { arguments?.getInt(WISH_ID) }

	private val presenter by viewModel<EditWishViewModel> { parametersOf(wishId) }

	private val createWishFunction = { name: String, info: String, cost: String, importance: String ->
		presenter.reduce(EditScreenEvent.ApplyClick(name = name, info = info, cost = cost, importance = importance))
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			val state = presenter.state.collectAsState()

			when (val renderedState = state.value) {
				is EditScreenState.InitialEditWish -> RenderEditWish(renderedState.wish)
				EditScreenState.InitialNewWish     -> RenderWishDetail(createWish = createWishFunction)
				EditScreenState.WishSaved          -> requireFragmentManager().popBackStack()
			}
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

	companion object {

		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().apply {
				wishId?.let { arguments = bundleOf(WISH_ID to wishId) }
			}
	}
}