package ru.endroad.shared.wish.core.domain

import ru.endroad.shared.wish.core.entity.Wish

interface DeleteWishUseCase {

	//TODO незачем передавать всю сущность целиком, оптимизировать
	suspend operator fun invoke(wish: Wish)
}