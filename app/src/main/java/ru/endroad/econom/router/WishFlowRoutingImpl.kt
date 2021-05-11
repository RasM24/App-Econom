package ru.endroad.econom.router

import ru.endroad.component.core.Navigator
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.completed.view.CompletedWishesFragment
import ru.endroad.feature.wish.detail.view.EditWishFragment

class WishFlowRoutingImpl(private val navigator: Navigator) : WishFlowRouting {

	override fun openWishEditScreen(wishId: Int) = navigator.open(EditWishFragment.getInstance(wishId))

	override fun openWishNewScreen() = navigator.open(EditWishFragment.getInstance())

	override fun openCompletedWishScreen() {
		navigator.open(CompletedWishesFragment())
	}
}