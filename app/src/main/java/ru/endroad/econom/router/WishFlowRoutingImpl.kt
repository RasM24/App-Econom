package ru.endroad.econom.router

import ru.endroad.econom.feature.wish.active.presenter.WishFlowRouting
import ru.endroad.econom.state.ApplicationState
import ru.endroad.econom.state.StateHolder

class WishFlowRoutingImpl(private val stateHolder: StateHolder) : WishFlowRouting {

	override fun openEditWishScreen(wishId: Int) = stateHolder.openScreen(ApplicationState.WishDetail(wishId))

	override fun openNewWishScreen() = stateHolder.openScreen(ApplicationState.WishDetail())

	override fun openCompletedWishScreen() = stateHolder.openScreen(ApplicationState.WishCompleted)
}