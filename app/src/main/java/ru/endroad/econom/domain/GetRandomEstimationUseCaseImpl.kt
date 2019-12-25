package ru.endroad.econom.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import ru.endroad.arena.data.bgDispatcher
import ru.endroad.econom.entity.Estimation
import ru.endroad.econom.presenter.GetRandomEstimationUseCase

class GetRandomEstimationUseCaseImpl(
	private val estimationRepository: EstimationRepository
) : GetRandomEstimationUseCase, CoroutineScope by CoroutineScope(bgDispatcher) {

	override fun invoke(): Deferred<Estimation> = async {
		estimationRepository
			.getListAsync()
			.await()
			.random()
	}
}