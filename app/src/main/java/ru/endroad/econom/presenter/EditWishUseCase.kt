package ru.endroad.econom.presenter

import ru.endroad.econom.entity.Wish

interface EditWishUseCase {
	operator fun invoke(wish: Wish)
}