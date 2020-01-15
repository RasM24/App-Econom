package ru.endroad.econom.component.wish.datasource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

class WishRepository(
	private val wishDataSource: WishDao
) : CoroutineScope by CoroutineScope(bgDispatcher) {

	fun getList(): Flow<WishList> = wishDataSource.getList()

	suspend fun get(id: Int): Wish = withContext(bgDispatcher) { wishDataSource.get(id) }

	suspend fun update(wish: Wish) = withContext(bgDispatcher) { wishDataSource.update(wish) }

	suspend fun insert(wish: Wish) = withContext(bgDispatcher) { wishDataSource.insert(wish) }

	suspend fun delete(wish: Wish) = withContext(bgDispatcher) { wishDataSource.delete(wish) }
}