package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

interface IWishListViewModel {

	val data: LiveData<WishList>
	fun getInfo(wish: Wish)
	fun perform(wish: Wish)
	fun delete(wish: Wish)
	fun calculateEstimationAsync(sum: Int): Deferred<TotalResult>
	fun openNewWishScreen()
}