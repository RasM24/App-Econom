package ru.endroad.econom.router

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.endroad.component.core.ContentNavigator
import ru.endroad.component.core.Navigator
import ru.endroad.econom.R
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.completed.presenter.WishCompletedListRouter
import ru.endroad.feature.wish.detail.presentation.WishDetailRouter

val routerModule = module {
	single<Navigator> { ContentNavigator(R.id.root) }

	singleBy<WishFlowRouting, WishFlowRoutingImpl>()
	singleBy<WishCompletedListRouter, WishCompletedListRouterImpl>()
	singleBy<WishDetailRouter, WishDetailRouterImpl>()
}