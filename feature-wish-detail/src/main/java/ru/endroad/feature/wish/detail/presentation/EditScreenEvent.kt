package ru.endroad.feature.wish.detail.presentation

sealed class EditScreenEvent {
	object LoadDraft : EditScreenEvent()
	class ApplyClick(val name: String, val cost: String, val importance: String, val info: String) : EditScreenEvent()
	object Back : EditScreenEvent()
}