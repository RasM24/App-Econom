package ru.endroad.econom.component.wish.domain

import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.model.WishList

class GetWishListLiveDataUseCase(private val wishRepository: WishRepository) {

	operator fun invoke(): Flow<WishList> = wishRepository.getList()
}