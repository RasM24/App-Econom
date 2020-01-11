package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.arena.mvi.viewmodel.PresenterMviAbstract
import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.econom.component.wish.domain.DeleteWishUseCase
import ru.endroad.econom.component.wish.domain.GetWishListUseCase
import ru.endroad.econom.component.wish.domain.PerformWishUseCase
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.*
import ru.endroad.econom.feature.wishes.entity.ListScreenState

class WishListViewModel(
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val getRandomEstimation: GetRandomEstimationUseCase,
	private val router: WishFlowRouting,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<ListScreenState, ListScreenEvent>() {

	init {
		viewModelScope.launch {
			getWishList().collect { wishList ->
				val notCompletedList = wishList.filterNot(Wish::complete)
				val sum = notCompletedList.sumBy(Wish::cost)

				ListScreenState.ShowData(notCompletedList, calculateEstimation(sum)).applyState()
			}
		}
	}

	override fun reduce(event: ListScreenEvent) {
		when (event) {
			is NewWishClick -> router.openWishNewScreen()
			is PerformClick -> viewModelScope.launch { performWish(event.wish) }
			is DeleteClick  -> viewModelScope.launch { deleteWish(event.wish) }
			is EditClick    -> router.openWishEditScreen(event.wish.id)
		}
	}

	//TODO разобраться с фичей дразнилки, какая то кривая реализация всего этого
	private fun calculateEstimation(sum: Int): TotalResult {
		val estimation = getRandomEstimation()

		return TotalResult(estimation.message, (sum / estimation.moneyRate).toInt())
	}
}