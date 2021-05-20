package ru.endroad.econom.feature.wishes.completed.mvi

import ru.endroad.shared.wish.core.entity.Wish

sealed class CompletedScreenEvent {

	class ChangeData(val completedWishList: List<Wish>) : CompletedScreenEvent()
	object Back : CompletedScreenEvent()
}