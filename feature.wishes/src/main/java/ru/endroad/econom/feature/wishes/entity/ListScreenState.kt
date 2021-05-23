package ru.endroad.econom.feature.wishes.entity

import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenState {
	object Idle : ListScreenState()
	object NoDesire : ListScreenState()
	object AllCompleted : ListScreenState()
	class ShowData(val wishList: List<Wish>) : ListScreenState()
}