package ru.endroad.shared.wish.core.domain

import kotlinx.coroutines.flow.Flow
import ru.endroad.shared.wish.core.entity.Wish

interface GetWishListUseCase {

	operator fun invoke(): Flow<List<Wish>>
}