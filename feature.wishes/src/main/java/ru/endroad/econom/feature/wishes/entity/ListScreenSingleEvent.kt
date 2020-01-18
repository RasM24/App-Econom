package ru.endroad.econom.feature.wishes.entity

import ru.endroad.econom.component.wish.model.Wish

sealed class ListScreenSingleEvent {
	class PerformWish(val wish: Wish) : ListScreenSingleEvent()
	class DeleteWish(val wish: Wish) : ListScreenSingleEvent()
}