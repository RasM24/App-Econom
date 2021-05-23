package ru.endroad.econom.feature.wishes.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wishes.WishFlowRouting
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
class WishListViewPresenter(
	private val addWish: AddWishUseCase,
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val router: WishFlowRouting,
	getWishList: GetWishListUseCase,
) : PresenterMviAbstract<ListScreenState, ListScreenEvent>() {

	val message = MutableSharedFlow<ListScreenSingleEvent?>()

	override val state = MutableStateFlow<ListScreenState>(ListScreenState.Init)

	//TODO переделать в отправку ивентов, как на скрине выполненных
	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishList().collect { wishList ->
				val notCompletedList = wishList.filterNot(Wish::complete).reversed()

				when {
					notCompletedList.isNotEmpty() -> ListScreenState.ShowData(notCompletedList).applyState()
					wishList.none(Wish::complete) -> ListScreenState.NoDesire.applyState()
					wishList.any(Wish::complete)  -> ListScreenState.AllCompleted.applyState()
				}
			}
		}
	}

	override fun reduce(event: ListScreenEvent) {
		when (event) {
			is NewWishClick     -> router.openWishNewScreen()
			is PerformClick     -> CoroutineScope(Dispatchers.Main).launch {
				performWish(event.wish)
				message.emit(ListScreenSingleEvent.PerformWish(event.wish))
			}
			is DeleteClick      -> CoroutineScope(Dispatchers.Main).launch {
				deleteWish(event.wish)
				message.emit(ListScreenSingleEvent.DeleteWish(event.wish))
			}
			is EditClick        -> router.openWishEditScreen(event.wish.id)
			MenuCompletedClick  -> router.openCompletedWishScreen()
			is UndoDeleteClick  -> CoroutineScope(Dispatchers.Main).launch { addWish(event.wish) }
			is UndoPerformClick -> CoroutineScope(Dispatchers.Main).launch { performWish(event.wish, complete = false) }
		}
	}
}