package ru.endroad.econom.component.wish.domain

import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.entity.Wish

internal class GetWishListUseCaseImpl(private val wishRepository: WishRepository) : GetWishListUseCase {

	override operator fun invoke(): Flow<List<Wish>> = wishRepository.getList()
}