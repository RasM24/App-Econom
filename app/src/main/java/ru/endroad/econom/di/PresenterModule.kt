package ru.endroad.econom.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.endroad.arena.viewmodellayer.viewModel
import ru.endroad.econom.domain.GetWishUseCase
import ru.endroad.econom.presenter.WishEditViewModel
import ru.endroad.econom.presenter.WishListViewModel
import ru.endroad.econom.view.EditWishState
import ru.endroad.econom.view.NewWishState

val presenterModule = module {

	viewModel<WishListViewModel>()

	factory { (id: Int?) ->
		if (id == null) NewWishState
		else EditWishState(get<GetWishUseCase> { parametersOf(id) }())
	}

	viewModel { (id: Int?) ->
		WishEditViewModel(
			addWish = get(),
			editWishUseCase = get(),
			state = get { parametersOf(id) }
		)
	}
}