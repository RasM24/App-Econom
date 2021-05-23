package ru.endroad.econom.feature.wish.active.presenter

interface WishFlowRouting {

	fun openWishEditScreen(wishId: Int)

	fun openWishNewScreen()

	fun openCompletedWishScreen()
}