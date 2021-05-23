package ru.endroad.econom.feature.wishes.completed.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishListPresenter(
	private val router: WishCompletedListRouter,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<CompletedScreenState>() {

	override val state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Init)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishList().collect { wishList ->
				val completedWishes = wishList.filter(Wish::complete)

				CompletedScreenState.ShowData(completedWishes).applyState()
			}
		}
	}

	fun back() {
		router.close()
	}
}

