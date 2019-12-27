package ru.endroad.econom.di

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.domain.*

val domainModule = module {
	factory<GetWishUseCase> { (id: Int) -> GetWishUseCase(get(), id) }
	singleBy<GetWishListLiveDataUseCase, GetWishListLiveDataUseCase>()
	singleBy<PerformWishUseCase, PerformWishUseCase>()
	singleBy<DeleteWishUseCase, DeleteWishUseCase>()
	singleBy<EditWishUseCase, EditWishUseCase>()
	singleBy<AddWishUseCase, AddWishUseCase>()
}