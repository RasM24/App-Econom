package ru.endroad.econom.component.wish

import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.component.wish.datasource.ImportanceConverter
import ru.endroad.econom.component.wish.datasource.WishModelConverter
import ru.endroad.econom.component.wish.datasource.WishRepository
import ru.endroad.econom.component.wish.domain.AddWishUseCaseImpl
import ru.endroad.econom.component.wish.domain.DeleteWishUseCaseImpl
import ru.endroad.econom.component.wish.domain.EditWishUseCaseImpl
import ru.endroad.econom.component.wish.domain.GetWishListUseCaseImpl
import ru.endroad.econom.component.wish.domain.GetWishUseCaseImpl
import ru.endroad.econom.component.wish.domain.PerformWishUseCaseImpl
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.DeleteWishUseCase
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishListUseCase
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.domain.PerformWishUseCase

val componentWishModule = module {
	single<ImportanceConverter>()
	single<WishModelConverter>()

	single<WishRepository>()

	singleBy<GetWishUseCase, GetWishUseCaseImpl>()
	singleBy<GetWishListUseCase, GetWishListUseCaseImpl>()
	singleBy<PerformWishUseCase, PerformWishUseCaseImpl>()
	singleBy<DeleteWishUseCase, DeleteWishUseCaseImpl>()
	singleBy<EditWishUseCase, EditWishUseCaseImpl>()
	singleBy<AddWishUseCase, AddWishUseCaseImpl>()
}