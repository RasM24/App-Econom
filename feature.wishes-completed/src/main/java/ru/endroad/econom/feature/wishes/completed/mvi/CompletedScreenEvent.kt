package ru.endroad.econom.feature.wishes.completed.mvi

import ru.endroad.econom.component.wish.model.WishList

sealed class CompletedScreenEvent {

	class ChangeData(val completedWishList: WishList) : CompletedScreenEvent()
}