package ru.endroad.birusa.feature.estimation

interface GetRandomEstimationUseCase {
	operator fun invoke(): Estimation
}