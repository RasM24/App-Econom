package ru.endroad.econom.feature.wishes.entity

import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenState {
	object Idle : ListScreenState()
	class Data(val wishList: List<Wish>, val hasCompletedWish: Boolean) : ListScreenState()
}