package ru.endroad.econom.router

import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.feature.wishes.completed.presenter.WishCompletedListRouter
import ru.endroad.econom.feature.wishes.presenter.WishFlowRouting
import ru.endroad.econom.state.StateHolder
import ru.endroad.feature.wish.detail.presentation.WishDetailRouter

val routerModule = module {
	single<StateHolder>()

	singleBy<WishFlowRouting, WishFlowRoutingImpl>()
	singleBy<WishCompletedListRouter, WishCompletedListRouterImpl>()
	singleBy<WishDetailRouter, WishDetailRouterImpl>()
}