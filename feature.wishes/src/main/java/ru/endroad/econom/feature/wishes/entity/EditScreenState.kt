package ru.endroad.econom.feature.wishes.entity

import ru.endroad.econom.component.wish.model.Wish

sealed class EditScreenState {

	object InitialNewWish : EditScreenState()
	class InitialEditWish(val wish: Wish) : EditScreenState()

	//TODO разделить валидацию nameField
	//TODO Вместо boolean использоать VALIDATE/INVALIDATE
	data class Validating(val nameField: Boolean? = null, val costField: Boolean? = null, val importanceField: Boolean? = null) : EditScreenState()
}