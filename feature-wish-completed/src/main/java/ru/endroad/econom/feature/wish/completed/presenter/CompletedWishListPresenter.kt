package ru.endroad.econom.feature.wish.completed.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishListPresenter(
	private val router: WishCompletedListRouter,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<CompletedScreenState>() {

	override val state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Idle)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishList().collect { wishList ->
				val completedWishes = wishList.filter(Wish::complete)

				CompletedScreenState.Data(completedWishes).applyState()
			}
		}
	}

	fun back() {
		router.close()
	}
}

