package ru.endroad.econom.domain

import ru.endroad.econom.entity.Wish

class EditWishUseCase(private val repository: WishRepository) {

	operator fun invoke(wish: Wish) = repository.update(wish)

}