package ru.endroad.econom.presenter

import kotlinx.coroutines.Deferred
import ru.endroad.econom.entity.Estimation

interface GetRandomEstimationUseCase {
	operator fun invoke(): Deferred<Estimation>
}