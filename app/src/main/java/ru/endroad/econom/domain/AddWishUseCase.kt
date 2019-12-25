package ru.endroad.econom.domain

import ru.endroad.econom.data.WishRepository
import ru.endroad.econom.entity.Wish

class AddWishUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(wish: Wish) = wishRepository.insert(wish)
}