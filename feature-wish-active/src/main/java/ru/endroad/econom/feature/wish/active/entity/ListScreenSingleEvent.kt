package ru.endroad.econom.feature.wish.active.entity

import ru.endroad.shared.wish.core.entity.Wish

sealed class ListScreenSingleEvent {
	object Nothing : ListScreenSingleEvent()
	class PerformWish(val wish: Wish) : ListScreenSingleEvent()
	class DeleteWish(val wish: Wish) : ListScreenSingleEvent()
}