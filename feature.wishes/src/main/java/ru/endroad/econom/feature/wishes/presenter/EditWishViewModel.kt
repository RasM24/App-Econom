package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.endroad.arena.mvi.viewmodel.PresenterMviAbstract
import ru.endroad.econom.component.wish.domain.AddWishUseCase
import ru.endroad.econom.component.wish.domain.EditWishUseCase
import ru.endroad.econom.component.wish.domain.GetWishUseCase
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.domain.CostValidator
import ru.endroad.econom.feature.wishes.domain.ImportanceValidator
import ru.endroad.econom.feature.wishes.domain.NameValidator
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent.*
import ru.endroad.econom.feature.wishes.entity.EditScreenState

class EditWishViewModel(
	private val wishId: Int?,
	private val getWish: GetWishUseCase,
	private val addWish: AddWishUseCase,
	private val editWish: EditWishUseCase,
	private val nameValidator: NameValidator,
	private val costValidator: CostValidator,
	private val importanceValidator: ImportanceValidator
) : PresenterMviAbstract<EditScreenEvent, EditScreenState>() {

	private val initialState: EditScreenState?
		get() = wishId?.let { EditScreenState.InitialEditWish(viewModelScope.async { getWish(wishId) }) }
			?: EditScreenState.InitialNewWish

	init {
		initialState?.applyState()
	}

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
		val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
		return EditScreenState.Validating(nameField = nameField)
	}

	private fun CostInputLostFocus.reduce() = EditScreenState.Validating(costField = costValidator(cost))

	private fun ImportanceInputLostFocus.reduce() = EditScreenState.Validating(importanceField = importanceValidator(importance))

	private fun NameInputReceiveFocus.reduce() = EditScreenState.Validating(nameField = true)

	private fun CostInputReceiveFocus.reduce() = EditScreenState.Validating(costField = true)

	private fun ImportanceInputReceiveFocus.reduce() = EditScreenState.Validating(importanceField = true)

	private fun ApplyClick.reduceAndApply() {
		val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
		val costField = costValidator(cost)
		val importanceField = importanceValidator(importance)

		if (nameField && costField && importanceField)
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