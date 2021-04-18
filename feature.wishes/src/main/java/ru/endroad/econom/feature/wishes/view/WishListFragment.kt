package ru.endroad.econom.feature.wishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.itemanimators.SlideInOutRightAnimator
import kotlinx.android.synthetic.main.wish_fragment_list.fragment_root
import kotlinx.android.synthetic.main.wish_fragment_list.list
import kotlinx.android.synthetic.main.wish_fragment_list.new_wish
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.feature.wishes.entity.ItemAction
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel
import ru.endroad.shared.wish.core.entity.Wish

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListFragment : Fragment() {

	private val presenter by viewModel<WishListViewModel>()

	private val adapter = WishAdapter(onItemClick = { showBottomSheet(it) })

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.wish_fragment_list, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		setHasOptionsMenu(true)

		requireActivity().title = "Сколько еще копить?"

		val divider = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
			.apply { setDrawable(resources.getDrawable(R.drawable.divider_horizontal)) }
		list.itemAnimator = SlideInOutRightAnimator(list)
		list.adapter = adapter
		list.addItemDecoration(divider)

		new_wish.setOnClickListener { presenter.reduce(ListScreenEvent.NewWishClick) }
		presenter.message.subscribe(this, messageHandler)

		presenter.state.asStateFlow()
			.onEach { render(it) }
			.launchIn(lifecycleScope)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.wish_list_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun render(state: ListScreenState) {
		when (state) {
			is ListScreenState.ShowData -> renderData(state)
			ListScreenState.NoData -> Unit
			ListScreenState.Init -> Unit
		}
	}

	private fun showBottomSheet(wish: Wish) {
		showBottomSheetActionWish(
			wish.name,
			onClickCompleteListener = { presenter.reduce(ListScreenEvent.PerformClick(wish)) },
			onClickEditListener = { presenter.reduce(ListScreenEvent.EditClick(wish)) },
			onClickDeleteListener = { presenter.reduce(ListScreenEvent.DeleteClick(wish)) }
		)
	}

	//TODO нужен рефактор
	private val messageHandler: (ListScreenSingleEvent?) -> Unit = { singleEvent: ListScreenSingleEvent? ->
		when (singleEvent) {
			is ListScreenSingleEvent.PerformWish -> {
				Snackbar.make(fragment_root, "Выполнено", LENGTH_LONG)
					.setAction("отменить") { presenter.reduce(ListScreenEvent.UndoDeleteClick(singleEvent.wish)) }
					.show()
			}

			is ListScreenSingleEvent.DeleteWish -> {
				Snackbar.make(fragment_root, "Удалено", LENGTH_LONG)
					.setAction("отменить") { presenter.reduce(ListScreenEvent.UndoDeleteClick(singleEvent.wish)) }
					.show()
			}
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menu_completed) presenter.reduce(ListScreenEvent.MenuCompletedClick)
		return true
	}

	private fun renderData(state: ListScreenState.ShowData) {
		val list = state.wishList

		when (state.changedItem?.action) {
			ItemAction.DELETED -> adapter.remove(state.changedItem.position)
			ItemAction.ADDED   -> adapter.add(state.changedItem.position, list[state.changedItem.position])
			else               -> adapter.items = list.toMutableList()
		}

//TODO может и вернуть
//		state.estimate
//			.map(::TotalItem)
//			.setFooter()
	}

	companion object {
		fun getInstance(): Fragment =
			WishListFragment()
	}
}