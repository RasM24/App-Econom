package ru.endroad.econom.feature.wishes.completed.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.component.wish.domain.GetWishListUseCase
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenEvent.ChangeData
import ru.endroad.econom.feature.wishes.completed.mvi.CompletedScreenState

class CompletedWishListViewModel(
	private val router: CompletedScreenRouting,
	getWishList: GetWishListUseCase
) : PresenterMviAbstract<CompletedScreenState, CompletedScreenEvent>() {

	override val state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Init)

	init {
		viewModelScope.launch {
			getWishList().collect { wishList ->
				val event = ChangeData(wishList.filter(Wish::complete))
				reduce(event)
			}
		}
	}

	override fun reduce(event: CompletedScreenEvent) {
		when (event) {
			is ChangeData -> event.reduceAndApply()
		}
	}

	private fun ChangeData.reduceAndApply() {
		when {
			completedWishList.isEmpty()    -> router.showStubNoCompleted()
			completedWishList.isNotEmpty() -> CompletedScreenState.ShowData(completedWishList).applyState()
		}
	}
}

