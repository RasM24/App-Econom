package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class DeleteWishUseCaseImpl(private val wishRepository: WishRepository): DeleteWishUseCase {

	override suspend fun invoke(wish: Wish) = wishRepository.delete(wish)
}