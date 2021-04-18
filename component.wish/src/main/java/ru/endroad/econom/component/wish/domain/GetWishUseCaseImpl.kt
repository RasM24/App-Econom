package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class GetWishUseCaseImpl(private val wishRepository: WishRepository) : GetWishUseCase {

	override suspend fun invoke(id: Int): Wish = wishRepository.get(id)
}