package ru.endroad.econom.feature.wishes.entity

import ru.endroad.econom.component.wish.model.Wish

sealed class ListScreenEvent {
	object MenuCompletedClick : ListScreenEvent()
	object NewWishClick : ListScreenEvent()
	class PerformClick(val wish: Wish) : ListScreenEvent()
	class DeleteClick(val wish: Wish) : ListScreenEvent()
	class EditClick(val wish: Wish) : ListScreenEvent()

	class UndoDeleteClick(val wish: Wish) : ListScreenEvent()
	class UndoPerformClick(val wish: Wish) : ListScreenEvent()
}