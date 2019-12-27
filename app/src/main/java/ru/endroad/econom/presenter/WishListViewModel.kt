package ru.endroad.econom.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.econom.component.wish.domain.DeleteWishUseCase
import ru.endroad.econom.component.wish.domain.GetWishListLiveDataUseCase
import ru.endroad.econom.component.wish.domain.PerformWishUseCase
import ru.endroad.econom.entity.TotalResult
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList
import ru.endroad.econom.view.IWishListViewModel

class WishListViewModel(
	private val deleteWish: DeleteWishUseCase,
	private val performWish: PerformWishUseCase,
	private val getRandomEstimation: GetRandomEstimationUseCase,
	getWishListLiveData: GetWishListLiveDataUseCase
) : ViewModel(),
	IWishListViewModel,
	CoroutineScope by CoroutineScope(bgDispatcher) {

	override val data: LiveData<WishList> = liveData { getWishListLiveData().collect(::emit) }

	override fun getInfo(wish: Wish) {}

	override fun calculateEstimationAsync(sum: Int): Deferred<TotalResult> = async {
		val estimation = getRandomEstimation()

		TotalResult(estimation.message, (sum / estimation.moneyRate).toInt())
	}

	override fun perform(wish: Wish) = performWish(wish)

	override fun delete(wish: Wish) = deleteWish(wish)
}