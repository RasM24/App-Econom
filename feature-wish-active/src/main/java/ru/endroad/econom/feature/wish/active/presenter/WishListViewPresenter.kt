package ru.endroad.econom.feature.wish.active.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wish.active.entity.ListScreenSingleEvent
import ru.endroad.econom.feature.wish.active.entity.ListScreenState
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.domain.PerformWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

class WishListViewPresenter(
	private val addWish: AddWishUseCase,
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val router: WishFlowRouting,
	getWishList: GetWishListUseCase,
) : PresenterMviAbstract<ListScreenState>() {

	val message = MutableSharedFlow<ListScreenSingleEvent?>()

	override val state = MutableStateFlow<ListScreenState>(ListScreenState.Idle)

	//TODO переделать в отправку ивентов, как на скрине выполненных
	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishList().collect { wishList ->
				ListScreenState.Data(
					wishList = wishList.filterNot(Wish::complete).reversed(),
					hasCompletedWish = wishList.any(Wish::complete),
				)
					.applyState()
			}
		}
	}

	fun openNewWishScreen() {
		router.openWishNewScreen()
	}

	fun openEditWishScreen(wishId: Int) {
		router.openWishEditScreen(wishId)
	}

	fun openCompletedWishScreen() {
		router.openCompletedWishScreen()
	}

	fun perform(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch {
			performWish(wish)
			message.emit(ListScreenSingleEvent.PerformWish(wish))
		}
	}

	fun delete(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch {
			deleteWish(wish)
			message.emit(ListScreenSingleEvent.DeleteWish(wish))
		}
	}

	fun undoDeleteWish(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch { addWish(wish) }
	}

	fun undoPerformWish(wish: Wish) {
		CoroutineScope(Dispatchers.Main).launch { performWish(wish, complete = false) }
	}
}