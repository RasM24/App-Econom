package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class AddWishUseCaseImpl(private val wishRepository: WishRepository) : AddWishUseCase {

	override suspend fun invoke(wish: Wish) = wishRepository.insert(wish)
}