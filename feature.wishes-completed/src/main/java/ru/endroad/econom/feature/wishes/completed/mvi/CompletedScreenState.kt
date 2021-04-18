package ru.endroad.econom.feature.wishes.completed.mvi

import ru.endroad.shared.wish.core.entity.Wish

sealed class CompletedScreenState {
	object Init : CompletedScreenState()
	class ShowData(val completedWishList: List<Wish>) : CompletedScreenState()
}