package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.entity.ChangedItem
import ru.endroad.econom.feature.wishes.entity.ItemAction
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.DeleteClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.EditClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.MenuCompletedClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.NewWishClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.PerformClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.UndoDeleteClick
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.UndoPerformClick
import ru.endroad.econom.feature.wishes.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenState
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.domain.PerformWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

//TODO чет работает плохо - разобраться
class WishListViewModel(
	private val addWish: AddWishUseCase,
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val getRandomEstimation: GetRandomEstimationUseCase,
	private val router: WishFlowRouting,
	getWishList: GetWishListUseCase,
) : PresenterMviAbstract<ListScreenState, ListScreenEvent>() {

	val message = MutableSharedFlow<ListScreenSingleEvent?>()

	override val state = MutableStateFlow<ListScreenState>(ListScreenState.Init)

	//TODO переделать в отправку ивентов, как на скрине выполненных
	init {
		viewModelScope.launch {
			getWishList().collect { wishList ->
				val notCompletedList = wishList.filterNot(Wish::complete).reversed()

				when {
					notCompletedList.isNotEmpty() -> state.value.reduce(notCompletedList).applyState()
					wishList.none(Wish::complete) -> ListScreenState.NoDesire.applyState()
					wishList.any(Wish::complete)  -> ListScreenState.AllCompleted.applyState()
				}
			}
		}
	}

	override fun reduce(event: ListScreenEvent) {
		when (event) {
			is NewWishClick     -> router.openWishNewScreen()
			is PerformClick     -> viewModelScope.launch {
				performWish(event.wish)
				message.emit(ListScreenSingleEvent.PerformWish(event.wish))
			}
			is DeleteClick      -> viewModelScope.launch {
				deleteWish(event.wish)
				message.emit(ListScreenSingleEvent.DeleteWish(event.wish))
			}
			is EditClick        -> router.openWishEditScreen(event.wish.id)
			MenuCompletedClick  -> router.openCompletedWishScreen()
			is UndoDeleteClick  -> viewModelScope.launch { addWish(event.wish) }
			is UndoPerformClick -> viewModelScope.launch { performWish(event.wish, complete = false) }
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

	private fun diff(oldList: List<Wish>, newList: List<Wish>): ChangedItem? {
		val itemAction: ItemAction = when (newList.size) {
			oldList.size + 1 -> ItemAction.ADDED
			oldList.size - 1 -> ItemAction.DELETED
			else             -> return null
		}

		val position = oldList.zip(newList)
			.indexOrLast { it.first.id != it.second.id }

		return ChangedItem(position, itemAction)
	}

	private inline fun <T> List<T>.indexOrLast(predicate: (T) -> Boolean): Int {
		val iterator = this.listIterator(size)
		while (iterator.hasPrevious()) {
			if (predicate(iterator.previous())) {
				return iterator.nextIndex()
			}
		}
		return size
	}
}