package ru.endroad.econom.feature.wish.completed.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

class CompletedWishListActor {

	private val router by inject(WishCompletedListRouter::class.java)
	private val getWishListUseCase by inject(GetWishListUseCase::class.java)

	private val _state = MutableStateFlow<CompletedScreenState>(CompletedScreenState.Idle)
	val state: StateFlow<CompletedScreenState> = _state

	fun loadData() {
		CoroutineScope(Dispatchers.Main).launch {
			getWishListUseCase().collect { wishList ->
				val completedWishes = wishList.filter(Wish::complete)
				_state.emit(CompletedScreenState.Data(completedWishes))
			}
		}
	}

	fun back() {
		router.close()
	}
}