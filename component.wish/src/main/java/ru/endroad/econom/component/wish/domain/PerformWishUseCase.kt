package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.Wish

class PerformWishUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(wish: Wish) {
		val completeWish = wish.copy(complete = true).apply { id = wish.id }
		wishRepository.update(completeWish)
	}
}