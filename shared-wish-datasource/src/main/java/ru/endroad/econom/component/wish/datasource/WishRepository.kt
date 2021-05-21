package ru.endroad.econom.component.wish.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.endroad.shared.wish.core.entity.Wish

internal class WishRepository(
	private val wishDataSource: WishDao,
	private val converter: WishModelConverter,
) {

	fun getList(): Flow<List<Wish>> = wishDataSource.getList().map { it.map(converter::to) }

	suspend fun get(id: Int): Wish = withContext(Dispatchers.IO) { wishDataSource.get(id).let(converter::to) }

	suspend fun update(wishModel: Wish) = withContext(Dispatchers.IO) { wishDataSource.update(wishModel.let(converter::from)) }

	suspend fun insert(wishModel: Wish) = withContext(Dispatchers.IO) { wishDataSource.insert(wishModel.let(converter::from)) }

	suspend fun delete(wishModel: Wish) = withContext(Dispatchers.IO) { wishDataSource.delete(wishModel.let(converter::from)) }
}