package ru.endroad.econom.feature.wishes.entity

import ru.endroad.birusa.feature.estimation.TotalResult
import ru.endroad.econom.component.wish.model.WishList

sealed class ListScreenState {
	class ShowData(val wishList: WishList, val estimate: TotalResult) : ListScreenState()
	object NoData : ListScreenState()
}