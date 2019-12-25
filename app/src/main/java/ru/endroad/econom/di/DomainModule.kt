package ru.endroad.econom.di

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.domain.*
import ru.endroad.econom.presenter.*

val domainModule = module {
	factory<GetWishUseCase> { (id: Int) -> GetWishUseCaseImpl(get(), id) }
	singleBy<GetRandomEstimationUseCase, GetRandomEstimationUseCaseImpl>()
	singleBy<GetWishListLiveDataUseCase, GetWishListLiveDataUseCaseImpl>()
	singleBy<PerformWishUseCase, PerformWishUseCaseImpl>()
	singleBy<DeleteWishUseCase, DeleteWishUseCaseImpl>()
	singleBy<EditWishUseCase, EditWishUseCaseImpl>()
	singleBy<AddWishUseCase, AddWishUseCaseImpl>()
}