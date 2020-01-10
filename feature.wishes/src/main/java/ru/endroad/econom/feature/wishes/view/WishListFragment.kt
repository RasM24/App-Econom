package ru.endroad.econom.feature.wishes.view

import androidx.fragment.app.Fragment
import com.mikepenz.fastadapter.IModelItem
import kotlinx.android.synthetic.main.wish_fragment_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.endroad.arena.data.flow.extension.subcribe
import ru.endroad.arena.data.uiDispatcher
import ru.endroad.arena.viewlayer.fragment.ListFragment
import ru.endroad.birusa.feature.estimation.TotalItem
import ru.endroad.birusa.feature.estimation.map
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel
import ru.endroad.navigation.forwardTo

//TODO перевести всю навигацию на роутинг в app-модуле
class WishListFragment : ListFragment(), CoroutineScope by CoroutineScope(uiDispatcher) {

	private val viewModel: IWishListViewModel by viewModel<WishListViewModel>()

	override val layout: Int = R.layout.wish_fragment_list

	override fun setupViewModel() {

		viewModel.data.subcribe(this) { wishList ->
			wishList
				.filterNot(Wish::complete) //TODO вынести в useCase
				.map(::WishItem)
				.setItems()

			launch {
				viewModel.calculateEstimationAsync(wishList
													   .filterNot(Wish::complete)
													   .sumBy(Wish::cost))
					.await()
					.map(::TotalItem)
					.setFooter()
			}
		}
	}

	override fun setupViewComponents() {
		title = "Сколько еще копить?"
		setDivider(R.drawable.divider_horizontal)
		fab.setOnClickListener {viewModel.openNewWishScreen()  }
	}

	override fun onClickItem(item: IModelItem<*, *>): Boolean {
		val model = (item.model as? Wish) ?: return false

		showBottomSheetActionWish(
			onClickCompleteListener = { viewModel.perform(model) },
			onClickInfoListener = { viewModel.getInfo(model) },
			onClickDeleteListener = { viewModel.delete(model) })

		return super.onClickItem(item)
	}

	companion object {
		fun getInstance(): Fragment =
			WishListFragment()
	}
}