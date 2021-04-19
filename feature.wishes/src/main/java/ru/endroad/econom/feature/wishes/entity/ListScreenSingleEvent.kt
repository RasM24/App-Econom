package ru.endroad.econom.feature.wishes.entity

import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenSingleEvent {
	class PerformWish(val wish: Wish) : ListScreenSingleEvent()
	class DeleteWish(val wish: Wish) : ListScreenSingleEvent()
}