package ru.endroad.econom.domain

import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.data.WishRepository
import ru.endroad.econom.presenter.WishList

class GetWishListLiveDataUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(): Flow<WishList> = wishRepository.getList()
}