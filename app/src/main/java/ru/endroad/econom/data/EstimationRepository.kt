package ru.endroad.econom.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.data.room.EstimationDao
import ru.endroad.econom.entity.Estimation

class EstimationRepository(
	private val dao: EstimationDao
) : CoroutineScope by CoroutineScope(bgDispatcher) {

	fun getListAsync(): Deferred<List<Estimation>> = async { dao.getList() }
}