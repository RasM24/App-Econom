package ru.endroad.econom.feature.wishes.entity

sealed class EditScreenEvent {
	class Apply(val name: String, val cost: String, val importance: String, val info: String) : EditScreenEvent()

	class NameInputLostFocus(val name: String) : EditScreenEvent()
	class InfoInputLostFocus(val info: String) : EditScreenEvent()
	class CostInputLostFocus(val cost: String) : EditScreenEvent()
	class ImportanceInputLostFocus(val importance: String) : EditScreenEvent()
}