package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.Wish

class EditWishUseCase(private val repository: WishRepository) {

	suspend operator fun invoke(wish: Wish) = repository.update(wish)

}