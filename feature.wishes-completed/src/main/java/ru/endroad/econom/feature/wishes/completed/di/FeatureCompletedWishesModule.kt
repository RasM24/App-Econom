package ru.endroad.econom.feature.wishes.completed.di

import org.koin.dsl.module
import org.koin.experimental.builder.factory
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListPresenter

val featureCompletedWishesModule = module {

	factory<CompletedWishListPresenter>()
}