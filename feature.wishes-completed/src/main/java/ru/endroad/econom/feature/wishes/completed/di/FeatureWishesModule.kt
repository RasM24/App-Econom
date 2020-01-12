package ru.endroad.econom.feature.wishes.completed.di

import org.koin.dsl.module
import ru.endroad.arena.viewmodellayer.viewModel
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedWishListViewModel

val featureWishesModule = module {

	viewModel<CompletedWishListViewModel>()
}