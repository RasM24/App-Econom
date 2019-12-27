package ru.endroad.birusa.feature.estimation

import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import ru.endroad.birusa.feature.estimation.data.EstimationDataSource
import ru.endroad.birusa.feature.estimation.domain.GetRandomEstimationUseCaseImpl

val featureEstimationModule = module {

	single<EstimationDataSource>()

	singleBy<GetRandomEstimationUseCase, GetRandomEstimationUseCaseImpl>()
}