package ru.endroad.econom.domain

import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.presenter.GetWishListLiveDataUseCase
import ru.endroad.econom.presenter.WishList

class GetWishListLiveDataUseCaseImpl(private val wishRepository: WishRepository) : GetWishListLiveDataUseCase {

	override fun invoke(): Flow<WishList> = wishRepository.getList()
}