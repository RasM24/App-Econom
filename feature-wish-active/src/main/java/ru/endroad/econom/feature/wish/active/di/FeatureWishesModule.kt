package ru.endroad.econom.feature.wish.active.di

import org.koin.dsl.module
import org.koin.experimental.builder.factory
import ru.endroad.econom.feature.wish.active.presenter.WishListViewPresenter

val featureWishesModule = module {
	factory<WishListViewPresenter>()
}