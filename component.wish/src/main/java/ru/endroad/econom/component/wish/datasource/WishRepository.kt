package ru.endroad.econom.component.wish.datasource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

class WishRepository(
	private val wishDataSource: WishDao
) : CoroutineScope by CoroutineScope(bgDispatcher) {

	fun getList(): Flow<WishList> = wishDataSource.getList()

	suspend fun get(id: Int): Wish = wishDataSource.get(id)

	fun update(wish: Wish) = launch { wishDataSource.update(wish) }.invokeOnCompletion { }

	fun insert(wish: Wish) = launch { wishDataSource.insert(wish) }

	fun delete(wish: Wish) = launch { wishDataSource.delete(wish) }
}