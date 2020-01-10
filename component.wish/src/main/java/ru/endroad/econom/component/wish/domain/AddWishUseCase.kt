package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.Wish

class AddWishUseCase(private val wishRepository: WishRepository) {

	suspend operator fun invoke(wish: Wish) = wishRepository.insert(wish)
}