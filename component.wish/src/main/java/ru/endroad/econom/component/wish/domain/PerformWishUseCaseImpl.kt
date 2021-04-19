package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.PerformWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class PerformWishUseCaseImpl(private val wishRepository: WishRepository) : PerformWishUseCase {

	override suspend fun invoke(wish: Wish, complete: Boolean) {
		val completeWish = wish.copy(complete = complete)
		wishRepository.update(completeWish)
	}
}