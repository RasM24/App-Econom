package ru.endroad.econom.domain

import ru.endroad.econom.presenter.DeleteWishUseCase
import ru.endroad.econom.entity.Wish

class DeleteWishUseCaseImpl(private val wishRepository: WishRepository) : DeleteWishUseCase {

	override fun invoke(wish: Wish) =
		wishRepository.delete(wish)
}