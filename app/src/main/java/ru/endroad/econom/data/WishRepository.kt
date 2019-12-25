package ru.endroad.econom.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.data.room.WishDao
import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.WishList

class WishRepository(
	private val wishDataSource: WishDao
) : CoroutineScope by CoroutineScope(bgDispatcher) {

	fun getList(): Flow<WishList> = wishDataSource.getList()

	fun getAsync(id: Int): Deferred<Wish> = async { wishDataSource.get(id) }

	fun update(wish: Wish) = launch { wishDataSource.update(wish) }.invokeOnCompletion { }

	fun insert(wish: Wish) = launch { wishDataSource.insert(wish) }

	fun delete(wish: Wish) = launch { wishDataSource.delete(wish) }

}