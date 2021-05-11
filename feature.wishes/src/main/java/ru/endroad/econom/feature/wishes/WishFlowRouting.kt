package ru.endroad.econom.feature.wishes

interface WishFlowRouting {

	fun openWishEditScreen(wishId: Int)

	fun openWishNewScreen()

	fun openCompletedWishScreen()
}