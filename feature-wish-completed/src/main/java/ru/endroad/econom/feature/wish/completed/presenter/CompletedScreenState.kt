package ru.endroad.econom.feature.wish.completed.presenter

import ru.endroad.shared.wish.core.entity.Wish

sealed class CompletedScreenState {
	object Idle : CompletedScreenState()
	class Data(val completedWishList: List<Wish>) : CompletedScreenState()
}