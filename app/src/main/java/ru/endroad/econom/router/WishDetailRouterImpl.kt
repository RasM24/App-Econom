package ru.endroad.econom.router

import ru.endroad.econom.state.StateHolder
import ru.endroad.feature.wish.detail.presentation.WishDetailRouter

class WishDetailRouterImpl(private val stateHolder: StateHolder) : WishDetailRouter {

	override fun close() {
		stateHolder.back()
	}
}