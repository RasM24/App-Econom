package ru.endroad.econom.domain

import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.AddWishUseCase

class AddWishUseCaseImpl(private val wishRepository: WishRepository) : AddWishUseCase {

	override fun invoke(wish: Wish) {
		wishRepository.insert(wish)
	}
}