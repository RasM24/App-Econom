package ru.endroad.econom.component.wish

import org.koin.dsl.module
import org.koin.experimental.builder.single
import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.domain.*

val componentWishModule = module {

	single<WishRepository>()

	single<GetWishUseCase>()
	single<GetWishListUseCase>()
	single<PerformWishUseCase>()
	single<DeleteWishUseCase>()
	single<EditWishUseCase>()
	single<AddWishUseCase>()
}