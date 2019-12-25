package ru.endroad.econom.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.data.room.WishDao
import ru.endroad.econom.domain.WishRepository
import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.WishList

class WishRepositoryImpl(
	private val wishDataSource: WishDao
) : WishRepository, CoroutineScope by CoroutineScope(bgDispatcher) {

	override fun getList(): Flow<WishList> =
		wishDataSource.getList()

	override fun getAsync(id: Int): Deferred<Wish> =
		async { wishDataSource.get(id) }

	override fun update(wish: Wish) {
		launch { wishDataSource.update(wish) }
	}

	override fun insert(wish: Wish) {
		launch { wishDataSource.insert(wish) }
	}

	override fun delete(wish: Wish) {
		launch { wishDataSource.delete(wish) }
	}
}