package ru.endroad.econom.feature.wishes.view

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.*
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.IModelItem
import kotlinx.android.synthetic.main.wish_fragment_list.*
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.arena.data.flow.extension.subcribe
import ru.endroad.arena.data.uiDispatcher
import ru.endroad.arena.mvi.view.MviView
import ru.endroad.arena.viewlayer.fragment.ListFragment
import ru.endroad.birusa.feature.estimation.TotalItem
import ru.endroad.birusa.feature.estimation.map
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListFragment : ListFragment(), MviView<ListScreenState, ListScreenEvent>, CoroutineScope by CoroutineScope(uiDispatcher) {

	override val presenter by viewModel<WishListViewModel>()

	override var toolbarMenu: Int? = R.menu.wish_list_menu

	override val layout: Int = R.layout.wish_fragment_list

	override val render = { state: ListScreenState ->
		when (state) {
			is ListScreenState.ShowData -> renderData(state)
			ListScreenState.NoData      -> Unit
		}
	}

	//TODO нужен рефактор
	private val messageHandler: (ListScreenSingleEvent) -> Unit = { singleEvent: ListScreenSingleEvent ->
		when (singleEvent) {
			is ListScreenSingleEvent.PerformWish -> {
				Snackbar.make(fragment_root, "Выполнено", LENGTH_LONG)
					.setAction("отменить") { presenter.reduce(ListScreenEvent.UndoDeleteClick(singleEvent.wish)) }
					.show()
			}

			is ListScreenSingleEvent.DeleteWish  -> {
				Snackbar.make(fragment_root, "Удалено", LENGTH_LONG)
					.setAction("отменить") { presenter.reduce(ListScreenEvent.UndoDeleteClick(singleEvent.wish)) }
					.show()
			}
		}
	}

	override fun setupViewComponents() {
		title = "Сколько еще копить?"
		setDivider(R.drawable.divider_horizontal)

		bindRenderState(this)
		new_wish.bindClick(ListScreenEvent::NewWishClick)
		presenter.message.subcribe(this, messageHandler)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menu_completed) presenter.reduce(ListScreenEvent.MenuCompletedClick)
		return true
	}

	override fun onClickItem(item: IModelItem<*, *>): Boolean {
		val model = item as? IModelItem<Wish, *> ?: return false

		showBottomSheetActionWish(
			model.model.name,
			onClickCompleteListener = model.bindItemEvent(ListScreenEvent::PerformClick),
			onClickEditListener = model.bindItemEvent(ListScreenEvent::EditClick),
			onClickDeleteListener = model.bindItemEvent(ListScreenEvent::DeleteClick))

		return super.onClickItem(item)
	}

	private fun renderData(state: ListScreenState.ShowData) {
		state.wishList
			.map(::WishItem)
			.setItems()

		state.estimate
			.map(::TotalItem)
			.setFooter()
	}

	private fun IModelItem<Wish, *>.bindItemEvent(onEvent: (Wish) -> ListScreenEvent): () -> Unit = { presenter.reduce(onEvent(model)) }

	companion object {
		fun getInstance(): Fragment =
			WishListFragment()
	}
}