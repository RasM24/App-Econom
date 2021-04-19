package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.econom.feature.wishes.domain.CostValidator
import ru.endroad.econom.feature.wishes.domain.ImportanceValidator
import ru.endroad.econom.feature.wishes.domain.NameValidator
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.ApplyClick
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.CostInputLostFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.CostInputReceiveFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.ImportanceInputLostFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.ImportanceInputReceiveFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.InfoInputLostFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.InfoInputReceiveFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.NameInputLostFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.NameInputReceiveFocus
import ru.endroad.econom.feature.wishes.entity.EditScreenState
import ru.endroad.econom.feature.wishes.entity.NameFieldValidate
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
			is ApplyClick                  -> event.reduceAndApply()

			is NameInputLostFocus          -> event.reduce().applyState()
			is InfoInputLostFocus          -> Unit
			is CostInputLostFocus          -> event.reduce().applyState()
			is ImportanceInputLostFocus    -> event.reduce().applyState()

			is NameInputReceiveFocus       -> event.reduce().applyState()
			is InfoInputReceiveFocus       -> Unit
			is CostInputReceiveFocus       -> event.reduce().applyState()
			is ImportanceInputReceiveFocus -> event.reduce().applyState()
		}
	}

	private fun NameInputLostFocus.reduce(): EditScreenState {
		val nameField = when {
			!nameValidator.isNotEmpty(name) -> NameFieldValidate.EMPTY
			!nameValidator.isNotLong(name)  -> NameFieldValidate.LONG
			else                            -> NameFieldValidate.VALIDATE
		}
		return EditScreenState.Validating(nameField = nameField)
	}

	private fun CostInputLostFocus.reduce() = EditScreenState.Validating(costField = costValidator(cost))

	private fun ImportanceInputLostFocus.reduce() = EditScreenState.Validating(importanceField = importanceValidator(importance))

	private fun NameInputReceiveFocus.reduce() = EditScreenState.Validating(nameField = NameFieldValidate.VALIDATE)

	private fun CostInputReceiveFocus.reduce() = EditScreenState.Validating(costField = true)

	private fun ImportanceInputReceiveFocus.reduce() = EditScreenState.Validating(importanceField = true)

	private fun ApplyClick.reduceAndApply() {
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