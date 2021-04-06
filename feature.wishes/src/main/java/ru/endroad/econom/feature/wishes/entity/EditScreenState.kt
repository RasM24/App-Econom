package ru.endroad.econom.feature.wishes.entity

import kotlinx.coroutines.Deferred
import ru.endroad.econom.component.wish.model.Wish

sealed class EditScreenState {

	object InitialNewWish : EditScreenState()
	class InitialEditWish(val wish: Deferred<Wish>) : EditScreenState()

	object WishSaved : EditScreenState()

	//TODO разделить валидацию nameField
	//TODO Вместо boolean использоать VALIDATE/INVALIDATE
	data class Validating(val nameField: NameFieldValidate? = null, val costField: Boolean? = null, val importanceField: Boolean? = null) : EditScreenState()
}

enum class NameFieldValidate {
	EMPTY,
	LONG,
	VALIDATE
}