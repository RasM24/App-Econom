package ru.endroad.econom.feature.wishes.di

import org.koin.dsl.module
import org.koin.experimental.builder.factory
import ru.endroad.econom.feature.wishes.presenter.WishListViewPresenter

val featureWishesModule = module {
	factory<WishListViewPresenter>()
}