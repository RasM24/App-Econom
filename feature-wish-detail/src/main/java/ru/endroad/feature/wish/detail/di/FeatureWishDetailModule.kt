package ru.endroad.feature.wish.detail.di

import org.koin.dsl.module
import ru.endroad.feature.wish.detail.presentation.EditWishViewPresenter

val featureWishDetailModule = module {
	factory { (id: Int?) ->
		EditWishViewPresenter(
			getWish = get(),
			addWish = get(),
			editWish = get(),
			router = get(),
			wishId = id,
		)
	}
}