package ru.endroad.econom.domain

import ru.endroad.econom.entity.Wish

class DeleteWishUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(wish: Wish) = wishRepository.delete(wish)
}