package ru.endroad.econom.feature.wishes.entity

import ru.endroad.econom.component.wish.model.Wish

sealed class ListScreenEvent {
	class NewWishClick : ListScreenEvent()
	class PerformClick(val wish: Wish) : ListScreenEvent()
	class DeleteClick(val wish: Wish) : ListScreenEvent()
	class EditClick(val wish: Wish) : ListScreenEvent()
}