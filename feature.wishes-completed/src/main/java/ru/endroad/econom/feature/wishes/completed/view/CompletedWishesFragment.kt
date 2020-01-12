package ru.endroad.econom.feature.wishes.completed.view

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.arena.mvi.view.MviView
import ru.endroad.arena.viewlayer.fragment.ListFragment
import ru.endroad.econom.feature.wishes.completed.R
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel

class CompletedWishesFragment : ListFragment(), MviView<CompletedScreenState, CompletedScreenEvent> {

	override val presenter by viewModel<CompletedWishListViewModel>()

	override val render = { state: CompletedScreenState ->
		when (state) {
			is CompletedScreenState.ShowData -> renderData(state)
		}
	}

	override fun setupViewComponents() {
		title = "Сколько еще копить?"
		setDivider(R.drawable.divider_horizontal)

		bindRenderState(this)
	}

	private fun renderData(state: CompletedScreenState.ShowData) {
		state.completedWishList
			.map(::CompletedWishItem)
			.setItems()
	}
}