package ru.endroad.econom.component.wish.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

class WishRepository(
	private val wishDataSource: WishDao
) {

	fun getList(): Flow<WishList> = wishDataSource.getList()

	suspend fun get(id: Int): Wish = withContext(Dispatchers.IO) { wishDataSource.get(id) }

	suspend fun update(wish: Wish) = withContext(Dispatchers.IO) { wishDataSource.update(wish) }

	suspend fun insert(wish: Wish) = withContext(Dispatchers.IO) { wishDataSource.insert(wish) }

	suspend fun delete(wish: Wish) = withContext(Dispatchers.IO) { wishDataSource.delete(wish) }
}