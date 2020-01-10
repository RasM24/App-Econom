package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.econom.component.wish.domain.DeleteWishUseCase
import ru.endroad.econom.component.wish.domain.GetWishListLiveDataUseCase
import ru.endroad.econom.component.wish.domain.PerformWishUseCase
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent.*
import ru.endroad.econom.feature.wishes.view.IWishListViewModel

class WishListViewModel(
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val getRandomEstimation: GetRandomEstimationUseCase,
	private val router: WishFlowRouting,
	getWishListLiveData: GetWishListLiveDataUseCase
) : ViewModel(),
	IWishListViewModel,
	CoroutineScope by CoroutineScope(bgDispatcher) {

	override fun reduce(event: ListScreenEvent) {
		when (event) {
			is NewWishClick -> router.openWishNewScreen()
			is PerformClick -> TODO()
			is DeleteClick  -> TODO()
			is EditClick    -> TODO()
		}
	}

	override val data: LiveData<WishList> = liveData { getWishListLiveData().collect(::emit) }

	override fun edit(wish: Wish) = router.openWishEditScreen(wish.id)

	override fun calculateEstimationAsync(sum: Int): Deferred<TotalResult> = async {
		val estimation = getRandomEstimation()

		TotalResult(estimation.message, (sum / estimation.moneyRate).toInt())
	}

	override fun perform(wish: Wish) {
		viewModelScope.launch { performWish(wish) }
	}

	override fun delete(wish: Wish) {
		viewModelScope.launch { deleteWish(wish) }
	}
}