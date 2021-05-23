package ru.endroad.econom.router

import ru.endroad.econom.feature.wish.completed.presenter.WishCompletedListRouter
import ru.endroad.econom.state.StateHolder

class WishCompletedListRouterImpl(private val stateHolder: StateHolder) : WishCompletedListRouter {

	override fun close() {
		stateHolder.back()
	}
}