package ru.endroad.econom.feature.wishes.presenter

interface WishFlowRouting {

	fun openWishEditScreen(wishId: Int)

	fun openWishNewScreen()

	fun openCompletedWishScreen()
}