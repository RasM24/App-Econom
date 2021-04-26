package ru.endroad.econom.feature.wishes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.endroad.econom.feature.wishes.presenter.WishListViewModel

val featureWishesModule = module {
	viewModel { WishListViewModel(get(), get(), get(), get(), get(), get()) }
}