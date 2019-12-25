package ru.endroad.econom.domain

import kotlinx.coroutines.Deferred
import ru.endroad.econom.data.WishRepository
import ru.endroad.econom.entity.Wish

class GetWishUseCase(private val wishRepository: WishRepository, private val idWish: Int) {

	operator fun invoke(): Deferred<Wish> =
		wishRepository.getAsync(idWish)
}