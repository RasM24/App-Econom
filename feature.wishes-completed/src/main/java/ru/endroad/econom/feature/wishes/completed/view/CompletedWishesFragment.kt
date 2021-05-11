package ru.endroad.econom.feature.wishes.completed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.component.core.setComposeView
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishesFragment : Fragment() {

	private val presenter by viewModel<CompletedWishListViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			val state = presenter.state.collectAsState()

			when (val screenState = state.value) {
				is CompletedScreenState.ShowData -> RenderData(screenState)
				else                             -> Unit
			}
		}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		requireActivity().title = "Сколько еще копить?"
	}

	@Composable
	private fun RenderData(state: CompletedScreenState.ShowData) {
		if (state.completedWishList.isEmpty()) {
			RenderNoCompletedStub()
		} else {
			LazyColumn(modifier = Modifier.fillMaxSize()) {
				items(state.completedWishList, Wish::id) { WishItem(wish = it) }
			}
		}
	}
}