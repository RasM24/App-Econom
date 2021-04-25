package ru.endroad.feature.wish.detail.presentation

sealed class EditScreenEvent {
	object LoadDraft : EditScreenEvent()
	class ApplyClick(val name: String, val cost: String, val importance: String, val info: String) : EditScreenEvent()

	class NameInputLostFocus(val name: String) : EditScreenEvent()
	class InfoInputLostFocus(val info: String) : EditScreenEvent()
	class CostInputLostFocus(val cost: String) : EditScreenEvent()
	class ImportanceInputLostFocus(val importance: String) : EditScreenEvent()

	class NameInputReceiveFocus : EditScreenEvent()
	class InfoInputReceiveFocus : EditScreenEvent()
	class CostInputReceiveFocus : EditScreenEvent()
	class ImportanceInputReceiveFocus : EditScreenEvent()
}