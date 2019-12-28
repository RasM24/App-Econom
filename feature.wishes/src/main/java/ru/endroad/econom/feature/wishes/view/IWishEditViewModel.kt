package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish

interface IWishEditViewModel {

	val state: StateW
	val validation: LiveData<FieldsValidation>
	fun applyData(name: String, cost: String, importance: String, info: String, id: Int = -1)
}

//TODO говнокод. Выпилить с переходом на MVI
data class FieldsValidation(val nameField: Boolean, val costField: Boolean, val importanceField: Boolean) {
	val validate get() = nameField && costField && importanceField

	companion object {
		fun validate(name: String, cost: String, importance: String): FieldsValidation {
			//TODO добавить валидацию на пустую строку имени
			val nameField = name.length in 1..40 //TODO magic number
			val costField = runCatching { cost.toInt() > 0 }.getOrDefault(false)
			val importanceField = runCatching { Importance.values().contains(Importance.valueOf(importance)) }.getOrDefault(false)

			return FieldsValidation(nameField, costField, importanceField)
		}
	}
}

sealed class StateW

object NewWishState : StateW()
class EditWishState(val wish: Deferred<Wish>) : StateW()