package ru.endroad.econom.feature.wishes.completed.mvi

import ru.endroad.econom.component.wish.model.WishList

sealed class CompletedScreenState {
	class ShowData(val completedWishList: WishList) : CompletedScreenState()
}