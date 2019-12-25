package ru.endroad.econom.presenter

import ru.endroad.econom.entity.Wish

interface DeleteWishUseCase {
	operator fun invoke(wish: Wish)
}