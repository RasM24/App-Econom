package ru.endroad.econom.feature.wishes.entity

sealed class ListScreenEvent {
	class NewWishClick : ListScreenEvent()
	class PerformClick : ListScreenEvent()
	class DeleteClick : ListScreenEvent()
	class EditClick : ListScreenEvent()
}