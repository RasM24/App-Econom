package ru.endroad.econom.presenter

import ru.endroad.econom.entity.Wish

interface AddWishUseCase {
	operator fun invoke(wish: Wish)
}