package ru.endroad.econom.feature.wishes.completed.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel

val featureCompletedWishesModule = module {

	viewModel { CompletedWishListViewModel(get()) }
}