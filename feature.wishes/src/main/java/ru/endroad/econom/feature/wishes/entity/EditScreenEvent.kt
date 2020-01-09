package ru.endroad.econom.feature.wishes.entity

sealed class EditScreenEvent {
	class ApplyClick(val name: String, val cost: String, val importance: String, val info: String) : EditScreenEvent()

	class NameInputChangeFocus(val name: String, val hasFocus: Boolean) : EditScreenEvent()
	class InfoInputChangeFocus(val info: String, val hasFocus: Boolean) : EditScreenEvent()
	class CostInputChangeFocus(val cost: String, val hasFocus: Boolean) : EditScreenEvent()
	class ImportanceInputChangeFocus(val importance: String, val hasFocus: Boolean) : EditScreenEvent()
}