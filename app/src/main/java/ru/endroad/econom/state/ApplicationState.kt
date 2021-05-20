package ru.endroad.econom.state

sealed class ApplicationState {

	object WishList : ApplicationState()
	class WishDetail(val wishId: Int? = null) : ApplicationState()
	object WishCompleted : ApplicationState()
}