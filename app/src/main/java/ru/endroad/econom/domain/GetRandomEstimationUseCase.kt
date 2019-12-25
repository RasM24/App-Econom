package ru.endroad.econom.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.entity.Estimation

class GetRandomEstimationUseCase(
	private val estimationRepository: EstimationRepository
) : CoroutineScope by CoroutineScope(bgDispatcher) {

	operator fun invoke(): Deferred<Estimation> = async {
		estimationRepository
			.getListAsync()
			.await()
			.random()
	}
}