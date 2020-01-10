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
import ru.endroad.econom.feature.wishes.entity.EditScreenState

class EditWishViewModel(
	private val wishId: Int?,
	private val getWish: GetWishUseCase,
	private val addWish: AddWishUseCase,
	private val editWishUseCase: EditWishUseCase,
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
			is EditScreenEvent.ApplyClick                  -> event.applyData()

			is EditScreenEvent.NameInputLostFocus          -> event.reduce()
			is EditScreenEvent.InfoInputLostFocus          -> Unit
			is EditScreenEvent.CostInputLostFocus          -> event.reduce()
			is EditScreenEvent.ImportanceInputLostFocus    -> event.reduce()

			is EditScreenEvent.NameInputReceiveFocus       -> event.reduce()
			is EditScreenEvent.InfoInputReceiveFocus       -> Unit
			is EditScreenEvent.CostInputReceiveFocus       -> event.reduce()
			is EditScreenEvent.ImportanceInputReceiveFocus -> event.reduce()
		}
	}

	private fun EditScreenEvent.NameInputLostFocus.reduce() {
		val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
		EditScreenState.Validating(nameField = nameField).applyState()
	}

	private fun EditScreenEvent.CostInputLostFocus.reduce() {
		EditScreenState.Validating(costField = costValidator(cost)).applyState()
	}

	private fun EditScreenEvent.ImportanceInputLostFocus.reduce() {
		EditScreenState.Validating(importanceField = importanceValidator(importance)).applyState()
	}

	private fun EditScreenEvent.NameInputReceiveFocus.reduce() {
		EditScreenState.Validating(nameField = true).applyState()
	}

	private fun EditScreenEvent.CostInputReceiveFocus.reduce() {
		EditScreenState.Validating(costField = true).applyState()
	}

	private fun EditScreenEvent.ImportanceInputReceiveFocus.reduce() {
		EditScreenState.Validating(importanceField = true).applyState()
	}

	private fun EditScreenEvent.ApplyClick.applyData() {
		val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
		val costField = costValidator(cost)
		val importanceField = importanceValidator(importance)

		EditScreenState.Validating(nameField, costField, importanceField).applyState()

		if (nameField && costField && importanceField) {
			val wish = Wish(name = name,
							info = info,
							cost = cost.toInt(),
							importance = Importance.valueOf(importance))

			viewModelScope.launch {
				wish.saving()
				EditScreenState.WishSaved.applyState()
			}
		}
	}

	private suspend fun Wish.saving() = wishId?.let { editWishUseCase(copy(id = it)) }
		?: addWish(this)
}