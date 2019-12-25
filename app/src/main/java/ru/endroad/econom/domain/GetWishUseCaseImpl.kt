package ru.endroad.econom.domain

import kotlinx.coroutines.Deferred
import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.GetWishUseCase

class GetWishUseCaseImpl(private val wishRepository: WishRepository, private val idWish: Int) : GetWishUseCase {

	override fun invoke(): Deferred<Wish> =
		wishRepository.getAsync(idWish)
}