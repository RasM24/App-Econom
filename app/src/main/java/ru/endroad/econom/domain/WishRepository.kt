package ru.endroad.econom.domain

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.WishList

interface WishRepository {

	fun getList(): Flow<WishList>
	fun getAsync(id: Int): Deferred<Wish>
	fun insert(wish: Wish)
	fun update(wish: Wish)
	fun delete(wish: Wish)
}