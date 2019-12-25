package ru.endroad.econom.domain

import kotlinx.coroutines.Deferred
import ru.endroad.econom.entity.Estimation

interface EstimationRepository {

	fun getListAsync(): Deferred<List<Estimation>>
}