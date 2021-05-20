package ru.endroad.econom.router

import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.state.ApplicationState
import ru.endroad.econom.state.StateHolder

class WishFlowRoutingImpl(private val stateHolder: StateHolder) : WishFlowRouting {

	override fun openWishEditScreen(wishId: Int) = stateHolder.openScreen(ApplicationState.WishDetail(wishId))

	override fun openWishNewScreen() = stateHolder.openScreen(ApplicationState.WishDetail())

	override fun openCompletedWishScreen() = stateHolder.openScreen(ApplicationState.WishCompleted)
}