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
			is PerformClick -> viewModelScope.launch { performWish(event.wish) }
			is DeleteClick  -> viewModelScope.launch { deleteWish(event.wish) }
			is EditClick    -> router.openWishEditScreen(event.wish.id)
		}
	}

	override val data: LiveData<WishList> = liveData { getWishListLiveData().collect(::emit) }

	override fun calculateEstimationAsync(sum: Int): Deferred<TotalResult> = async {
		val estimation = getRandomEstimation()

		TotalResult(estimation.message, (sum / estimation.moneyRate).toInt())
	}

}