package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.econom.component.wish.model.WishList
import ru.endroad.econom.feature.wishes.entity.ListScreenEvent

interface IWishListViewModel {

	fun reduce(event: ListScreenEvent)

	val data: LiveData<WishList>
	fun calculateEstimationAsync(sum: Int): Deferred<TotalResult>
}