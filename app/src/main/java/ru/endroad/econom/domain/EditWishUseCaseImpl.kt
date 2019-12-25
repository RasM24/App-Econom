package ru.endroad.econom.domain

import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.EditWishUseCase

class EditWishUseCaseImpl(private val repository: WishRepository) : EditWishUseCase {

	override fun invoke(wish: Wish) {
		repository.update(wish)
	}
}