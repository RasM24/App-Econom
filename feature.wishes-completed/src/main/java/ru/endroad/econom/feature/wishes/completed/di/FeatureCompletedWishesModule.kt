package ru.endroad.econom.feature.wishes.completed.di

import org.koin.dsl.module
import org.koin.experimental.builder.factory
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListPresenter

//TODO попробовать избавится от предоставления наружу необходимости резолва зависимостей
val featureCompletedWishesModule = module {

	factory<CompletedWishListPresenter>()
}