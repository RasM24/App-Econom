package ru.endroad.feature.wish.detail.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.endroad.feature.wish.detail.presentation.EditWishViewModel

val featureWishDetailModule = module {
	viewModel { (id: Int?) ->
		EditWishViewModel(
			getWish = get(),
			addWish = get(),
			editWish = get(),
			wishId = id,
		)
	}
}