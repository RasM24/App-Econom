package ru.endroad.econom.feature.wishes.completed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.econom.feature.wishes.completed.R
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel

class CompletedWishesFragment : Fragment() {

	private val presenter by viewModel<CompletedWishListViewModel>()

	private val adapter = WishAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.list_fragment, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		requireActivity().title = "Сколько еще копить?"


		val divider = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
			.apply { setDrawable(resources.getDrawable(R.drawable.divider_horizontal)) }
		list.adapter = adapter
		list.addItemDecoration(divider)

		presenter.state.asStateFlow()
			.onEach { render(it) }
			.launchIn(lifecycleScope)
	}

	private fun render(state: CompletedScreenState) {
		when (state) {
			is CompletedScreenState.ShowData -> renderData(state)
			else -> Unit
		}
	}

	private fun renderData(state: CompletedScreenState.ShowData) {
		adapter.items = state.completedWishList
	}
}