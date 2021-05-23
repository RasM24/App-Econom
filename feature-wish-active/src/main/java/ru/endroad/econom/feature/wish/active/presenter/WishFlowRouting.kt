package ru.endroad.econom.feature.wish.active.presenter

interface WishFlowRouting {

	fun openEditWishScreen(wishId: Int)

	fun openNewWishScreen()

	fun openCompletedWishScreen()
}