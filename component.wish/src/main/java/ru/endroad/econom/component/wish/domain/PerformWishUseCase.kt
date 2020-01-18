package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.Wish

class PerformWishUseCase(private val wishRepository: WishRepository) {

	suspend operator fun invoke(wish: Wish, complete: Boolean = true) {
		val completeWish = wish.copy(complete = complete).apply { id = wish.id }
		wishRepository.update(completeWish)
	}
}