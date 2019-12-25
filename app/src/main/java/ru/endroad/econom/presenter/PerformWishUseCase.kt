package ru.endroad.econom.presenter

import ru.endroad.econom.entity.Wish

interface PerformWishUseCase {
	operator fun invoke(wish: Wish)
}