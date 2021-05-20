package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class EditWishUseCaseImpl(private val repository: WishRepository) : EditWishUseCase {

	override suspend fun invoke(wish: Wish) = repository.update(wish)
}