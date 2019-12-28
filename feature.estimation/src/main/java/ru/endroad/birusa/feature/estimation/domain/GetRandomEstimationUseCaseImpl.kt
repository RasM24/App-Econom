package ru.endroad.birusa.feature.estimation.domain

import ru.endroad.birusa.feature.estimation.GetRandomEstimationUseCase
import ru.endroad.birusa.feature.estimation.data.EstimationDataSource
import ru.endroad.birusa.feature.estimation.Estimation

internal class GetRandomEstimationUseCaseImpl(
	private val estimationDataSource: EstimationDataSource
) : GetRandomEstimationUseCase {

	override fun invoke(): Estimation = estimationDataSource.get().random()
}