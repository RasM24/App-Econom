package ru.endroad.econom.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import ru.endroad.econom.entity.TotalResult
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

interface IWishListViewModel {

	val data: LiveData<WishList>
	fun getInfo(wish: Wish)
	fun perform(wish: Wish)
	fun delete(wish: Wish): Job
	fun calculateEstimationAsync(sum: Int): Deferred<TotalResult>
}