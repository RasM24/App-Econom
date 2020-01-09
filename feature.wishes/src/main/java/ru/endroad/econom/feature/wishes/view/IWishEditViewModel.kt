package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent

interface IWishEditViewModel {

	val state: StateW
	val validation: LiveData<FieldsValidation>

	fun event(event: EditScreenEvent)
}

//TODO говнокод. Выпилить с переходом на MVI
data class FieldsValidation(val nameField: Boolean, val costField: Boolean, val importanceField: Boolean) {

	val validate get() = nameField && costField && importanceField

}

sealed class StateW

object NewWishState : StateW()
class EditWishState(val wish: Deferred<Wish>) : StateW()

