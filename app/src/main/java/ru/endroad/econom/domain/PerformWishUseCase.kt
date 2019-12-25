package ru.endroad.econom.domain

import ru.endroad.econom.data.WishRepository
import ru.endroad.econom.entity.Wish

class PerformWishUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(wish: Wish) {
		val completeWish = wish.copy(complete = true).apply { id = wish.id }
		wishRepository.update(completeWish)
	}
}