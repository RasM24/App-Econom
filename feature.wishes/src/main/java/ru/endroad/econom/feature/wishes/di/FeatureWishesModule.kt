package ru.endroad.econom.feature.wishes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.experimental.builder.single
import ru.endroad.arena.viewmodellayer.viewModel
import ru.endroad.econom.feature.wishes.domain.CostValidator
import ru.endroad.econom.feature.wishes.domain.ImportanceValidator
import ru.endroad.econom.feature.wishes.domain.NameValidator
import ru.endroad.econom.feature.wishes.presenter.WishEditViewModel
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel
import ru.endroad.econom.feature.wishes.view.EditWishState
import ru.endroad.econom.feature.wishes.view.NewWishState

val featureWishesModule = module {

	single { NameValidator(40) } //TODO пока пусть будет как magicNumber
	single<CostValidator>()
	single<ImportanceValidator>()

	viewModel<WishListViewModel>()

	factory { (id: Int?) ->
		if (id == null) NewWishState
		else EditWishState(get())
	}

	viewModel { (id: Int?) ->
		WishEditViewModel(
			addWish = get(),
			editWishUseCase = get(),
			stateW = get { parametersOf(id) },
			nameValidator = get(),
			costValidator = get(),
			importanceValidator = get()
		)
	}
}