package ru.endroad.econom.presenter

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.entity.Wish

interface GetWishUseCase {
	operator fun invoke(): Deferred<Wish>
}