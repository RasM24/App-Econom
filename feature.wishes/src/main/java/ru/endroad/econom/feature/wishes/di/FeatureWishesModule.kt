package ru.endroad.econom.feature.wishes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single
import ru.endroad.econom.feature.wishes.domain.CostValidator
import ru.endroad.econom.feature.wishes.domain.ImportanceValidator
import ru.endroad.econom.feature.wishes.domain.NameValidator
import ru.endroad.econom.feature.wishes.presenter.EditWishViewModel
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel

val featureWishesModule = module {

	single { NameValidator(40) } //TODO пока пусть будет как magicNumber
	single<CostValidator>()
	single<ImportanceValidator>()

	viewModel { WishListViewModel(get(), get(), get(), get(), get(), get()) }

	viewModel { (id: Int?) ->
		EditWishViewModel(
			getWish = get(),
			addWish = get(),
			editWish = get(),
			wishId = id,
			nameValidator = get(),
			costValidator = get(),
			importanceValidator = get()
		)
	}
}