package ru.endroad.shared.wish.core.domain

import ru.endroad.shared.wish.core.entity.Wish

interface EditWishUseCase {

	suspend operator fun invoke(wish: Wish)
}