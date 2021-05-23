package ru.endroad.econom.feature.wishes.entity

import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenState {
	object Init : ListScreenState()
	class ShowData(val wishList: List<Wish>) : ListScreenState()

	object NoDesire : ListScreenState()
	object AllCompleted : ListScreenState()
}