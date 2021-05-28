package ru.endroad.econom.feature.wish.active.presenter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.econom.feature.wish.active.entity.ListScreenState
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.domain.PerformWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

class ActiveWishListActor {

	private val addWishUseCase by inject(AddWishUseCase::class.java)
	private val deleteWishUseCase by inject(DeleteWishUseCase::class.java)
	private val performWishUseCase by inject(PerformWishUseCase::class.java)
	private val getWishListUseCase by inject(GetWishListUseCase::class.java)
	private val router by inject(WishFlowRouting::class.java)

	private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Idle)
	val state: StateFlow<ListScreenState> = _state.asStateFlow()

	suspend fun fetchData() {
		getWishListUseCase()
			.map { ListScreenState.Data(wishList = it.filterNot(Wish::complete).reversed(), hasCompletedWish = it.any(Wish::complete)) }
			.onEach { _state.emit(it) }
			.collect()
	}

	suspend fun performWish(wish: Wish) {
		performWishUseCase(wish)
	}

	suspend fun deleteWish(wish: Wish) {
		deleteWishUseCase(wish)
	}

	suspend fun undoDeleteWish(wish: Wish) {
		addWishUseCase(wish)
	}

	suspend fun undoPerformWish(wish: Wish) {
		performWishUseCase(wish, complete = false)
	}

	fun openEditWishScreen(wishId: Int) {
		router.openEditWishScreen(wishId)
	}

	fun openNewWishScreen() {
		router.openNewWishScreen()
	}

	fun openCompletedWishScreen() {
		router.openCompletedWishScreen()
	}
}