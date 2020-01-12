package ru.endroad.econom.feature.wishes

interface WishFlowRouting {

	fun openWishEditScreen(wishId: Int)

	fun openWishNewScreen()

	fun showStubNoDesire()

	fun showStubWishesFulfilled()

	fun closeStub()
}