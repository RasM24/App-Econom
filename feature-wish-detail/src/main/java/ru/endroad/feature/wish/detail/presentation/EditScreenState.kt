package ru.endroad.feature.wish.detail.presentation

import kotlinx.coroutines.Deferred
import ru.endroad.feature.wish.detail.domain.ValidationResult
import ru.endroad.shared.wish.core.entity.Wish

sealed class EditScreenState {

	object InitialNewWish : EditScreenState()
	class InitialEditWish(val wish: Deferred<Wish>) : EditScreenState()

	object WishSaved : EditScreenState()

	//TODO разделить валидацию nameField
	//TODO Вместо boolean использоать VALIDATE/INVALIDATE
	data class Validating(
		val nameField: NameFieldValidate? = null,
		val costField: ValidationResult? = ValidationResult.Unchecked,
		val importanceField: ValidationResult? = ValidationResult.Unchecked
	) : EditScreenState()
}

enum class NameFieldValidate {
	EMPTY,
	LONG,
	VALIDATE
}