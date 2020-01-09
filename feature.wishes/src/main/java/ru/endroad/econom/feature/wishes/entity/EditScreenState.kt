package ru.endroad.econom.feature.wishes.entity

sealed class EditScreenState {

	object InitialNewWish : EditScreenState()
	class InitialEditWish(val id: Int) : EditScreenState()

	//TODO разделить валидацию nameField
	//TODO Вместо boolean использоать VALIDATE/INVALIDATE
	data class Validating(val nameField: Boolean? = null, val costField: Boolean? = null, val importanceField: Boolean? = null) : EditScreenState()
}