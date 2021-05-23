package ru.endroad.econom.feature.wishes.completed.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent.ChangeData
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishListPresenter(
	private val router: WishCompletedListRouter,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<CompletedScreenState, CompletedScreenEvent>() {

	override val state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Init)

	init {
		CoroutineScope(Dispatchers.Main).launch {
			getWishList().collect { wishList ->
				val event = ChangeData(wishList.filter(Wish::complete))
				reduce(event)
			}
		}
	}

	override fun reduce(event: CompletedScreenEvent) {
		when (event) {
			is ChangeData             -> CompletedScreenState.ShowData(event.completedWishList).applyState()
			CompletedScreenEvent.Back -> back()
		}
	}

	private fun back() {
		router.close()
	}
}

