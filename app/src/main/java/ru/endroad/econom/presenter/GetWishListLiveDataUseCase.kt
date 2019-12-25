package ru.endroad.econom.presenter

import kotlinx.coroutines.flow.Flow

interface GetWishListLiveDataUseCase {
	operator fun invoke(): Flow<WishList>
}