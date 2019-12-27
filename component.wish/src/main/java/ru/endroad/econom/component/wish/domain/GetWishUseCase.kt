package ru.endroad.econom.component.wish.domain

import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.Wish

class GetWishUseCase(private val wishRepository: WishRepository) {

	suspend operator fun invoke(id: Int): Wish = wishRepository.get(id)
}