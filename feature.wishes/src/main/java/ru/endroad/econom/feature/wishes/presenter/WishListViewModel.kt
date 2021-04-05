package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.component.core.SingleLiveEvent
import ru.endroad.econom.component.wish.domain.AddWishUseCase
import ru.endroad.econom.component.wish.domain.DeleteWishUseCase
import ru.endroad.econom.component.wish.domain.GetWishListUseCase
import ru.endroad.econom.component.wish.domain.PerformWishUseCase
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.entity.*
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.*

class WishListViewModel(
	private val addWish: AddWishUseCase,
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val getRandomEstimation: GetRandomEstimationUseCase,
	private val router: WishFlowRouting,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<ListScreenState, ListScreenEvent>() {

	val message = SingleLiveEvent<ListScreenSingleEvent>()

	override val state = MutableStateFlow<ListScreenState>(ListScreenState.Init)

	//TODO переделать в отправку ивентов, как на скрине выполненных
	init {
		viewModelScope.launch {
			getWishList().collect { wishList ->
				val notCompletedList = wishList.filterNot(Wish::complete).reversed()

				when {
					notCompletedList.isNotEmpty() -> {
						if (state.value == ListScreenState.NoData) router.closeStub()
						val currentState = state.value
						currentState.reduce(notCompletedList).applyState()
					}

					wishList.none(Wish::complete) -> {
						ListScreenState.NoData.applyState()
						router.showStubNoDesire()
					}

					wishList.any(Wish::complete)  -> {
						ListScreenState.NoData.applyState()
						router.showStubWishesFulfilled()
					}
				}
			}
		}
	}

	override fun reduce(event: ListScreenEvent) {
		when (event) {
			is NewWishClick     -> router.openWishNewScreen()
			is PerformClick     -> viewModelScope.launch {
				performWish(event.wish)
				message(ListScreenSingleEvent.PerformWish(event.wish))
			}
			is DeleteClick      -> viewModelScope.launch {
				deleteWish(event.wish)
				message(ListScreenSingleEvent.DeleteWish(event.wish))
			}
			is EditClick        -> router.openWishEditScreen(event.wish.id)
			MenuCompletedClick  -> router.openCompletedWishScreen()
			is UndoDeleteClick  -> viewModelScope.launch { addWish(event.wish) }
			is UndoPerformClick -> viewModelScope.launch { performWish(event.wish) }
		}
	}

	//TODO разобраться с фичей дразнилки, какая то кривая реализация всего этого
	private fun calculateEstimation(sum: Int): TotalResult {
		val estimation = getRandomEstimation()

		return TotalResult(estimation.message, (sum / estimation.moneyRate).toInt())
	}

	private fun ListScreenState?.reduce(notCompletedList: List<Wish>): ListScreenState {
		val sum = notCompletedList.sumBy(Wish::cost)

		return if (this is ListScreenState.ShowData) {
			val changedItem = diff(this.wishList, notCompletedList)
			ListScreenState.ShowData(notCompletedList, calculateEstimation(sum), changedItem)
		} else
			ListScreenState.ShowData(notCompletedList, calculateEstimation(sum))
	}

	private fun diff(oldList: WishList, newList: WishList): ChangedItem? {
		val itemAction: ItemAction = when (newList.size) {
			oldList.size + 1 -> ItemAction.ADDED
			oldList.size - 1 -> ItemAction.DELETED
			else -> return null
		}

		val position = oldList.zip(newList)
			.indexOfFirst { it.first.id != it.second.id }

		return ChangedItem(position, itemAction)
	}
}