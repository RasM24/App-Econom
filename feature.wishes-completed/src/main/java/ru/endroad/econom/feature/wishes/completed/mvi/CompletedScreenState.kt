package ru.endroad.econom.feature.wishes.completed.mvi

import ru.endroad.econom.component.wish.model.WishList

sealed class CompletedScreenState {
	object Init : CompletedScreenState()
	class ShowData(val completedWishList: WishList) : CompletedScreenState()
}