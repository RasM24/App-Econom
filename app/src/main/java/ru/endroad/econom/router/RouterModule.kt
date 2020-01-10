package ru.endroad.econom.router

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.feature.wishes.WishFlowRouting

val routerModule = module {
	singleBy<WishFlowRouting, WishFlowRoutingImpl>()
}