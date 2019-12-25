package ru.endroad.econom.domain

import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.PerformWishUseCase

class PerformWishUseCaseImpl(private val wishRepository: WishRepository) : PerformWishUseCase {

	override fun invoke(wish: Wish) {
		val completeWish = wish.copy(complete = true).apply { id = wish.id }
		wishRepository.update(completeWish)
	}
}