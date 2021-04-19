package ru.endroad.feature.wish.detail.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.feature.wish.detail.domain.CostValidator
import ru.endroad.feature.wish.detail.domain.ImportanceValidator
import ru.endroad.feature.wish.detail.domain.NameValidator

import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

class EditWishViewModel(
	private val wishId: Int?,
	private val getWish: GetWishUseCase,
	private val addWish: AddWishUseCase,
	private val editWish: EditWishUseCase,
	private val nameValidator: NameValidator,
	private val costValidator: CostValidator,
	private val importanceValidator: ImportanceValidator
) : PresenterMviAbstract<EditScreenState, EditScreenEvent>() {

	private val initialState: EditScreenState
		get() = wishId?.let { EditScreenState.InitialEditWish(viewModelScope.async { getWish(wishId) }) }
			?: EditScreenState.InitialNewWish

	override val state: MutableStateFlow<EditScreenState> = MutableStateFlow(initialState)

	override fun reduce(event: EditScreenEvent) {
		when (event) {
			is EditScreenEvent.ApplyClick                  -> event.reduceAndApply()

			is EditScreenEvent.NameInputLostFocus          -> event.reduce().applyState()
			is EditScreenEvent.InfoInputLostFocus          -> Unit
			is EditScreenEvent.CostInputLostFocus          -> event.reduce().applyState()
			is EditScreenEvent.ImportanceInputLostFocus    -> event.reduce().applyState()

			is EditScreenEvent.NameInputReceiveFocus       -> event.reduce().applyState()
			is EditScreenEvent.InfoInputReceiveFocus       -> Unit
			is EditScreenEvent.CostInputReceiveFocus       -> event.reduce().applyState()
			is EditScreenEvent.ImportanceInputReceiveFocus -> event.reduce().applyState()
		}
	}

	private fun EditScreenEvent.NameInputLostFocus.reduce(): EditScreenState {
		val nameField = when {
			!nameValidator.isNotEmpty(name) -> NameFieldValidate.EMPTY
			!nameValidator.isNotLong(name)  -> NameFieldValidate.LONG
			else                            -> NameFieldValidate.VALIDATE
		}
		return EditScreenState.Validating(nameField = nameField)
	}

	private fun EditScreenEvent.CostInputLostFocus.reduce() = EditScreenState.Validating(costField = costValidator(cost))

	private fun EditScreenEvent.ImportanceInputLostFocus.reduce() = EditScreenState.Validating(importanceField = importanceValidator(importance))

	private fun EditScreenEvent.NameInputReceiveFocus.reduce() = EditScreenState.Validating(nameField = NameFieldValidate.VALIDATE)

	private fun EditScreenEvent.CostInputReceiveFocus.reduce() = EditScreenState.Validating(costField = true)

	private fun EditScreenEvent.ImportanceInputReceiveFocus.reduce() = EditScreenState.Validating(importanceField = true)

	private fun EditScreenEvent.ApplyClick.reduceAndApply() {
		val nameField = when {
			!nameValidator.isNotEmpty(name) -> NameFieldValidate.EMPTY
			!nameValidator.isNotLong(name)  -> NameFieldValidate.LONG
			else                            -> NameFieldValidate.VALIDATE
		}
		val costField = costValidator(cost)
		val importanceField = importanceValidator(importance)

		if (nameField == NameFieldValidate.VALIDATE && costField && importanceField)
			viewModelScope.launch { saveWish(name, info, cost.toInt(), Importance.valueOf(importance)).applyState() }
		else
			EditScreenState.Validating(nameField, costField, importanceField).applyState()
	}

	//TODO можно вынести в domain
	private suspend fun saveWish(name: String, info: String, cost: Int, importance: Importance): EditScreenState {
		val wish = Wish(name = name, info = info, cost = cost, importance = importance)
		wishId?.let { editWish(wish.copy(id = it)) } ?: addWish(wish)

		return EditScreenState.WishSaved
	}
}