package ru.endroad.econom.feature.wishes.entity

import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenState {
	object Init : ListScreenState()
	class ShowData(
		val wishList: List<Wish>,
		val estimate: TotalResult,
		val changedItem: ChangedItem? = null
	) : ListScreenState()

	object NoData : ListScreenState()
}

data class ChangedItem(val position: Int, val action: ItemAction)

enum class ItemAction {
	DELETED,
	ADDED
}