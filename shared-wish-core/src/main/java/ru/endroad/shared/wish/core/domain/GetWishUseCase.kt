package ru.endroad.shared.wish.core.domain

import ru.endroad.shared.wish.core.entity.Wish

interface GetWishUseCase {

	suspend operator fun invoke(id: Int): Wish
}