package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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
import ru.endroad.econom.feature.wishes.view.IWishEditViewModel

class EditWishViewModel(
	private val wishId: Int?,
	private val getWish: GetWishUseCase,
	private val addWish: AddWishUseCase,
	private val editWishUseCase: EditWishUseCase,
	private val nameValidator: NameValidator,
	private val costValidator: CostValidator,
	private val importanceValidator: ImportanceValidator
) : ViewModel(), IWishEditViewModel {

	override val state = MutableLiveData<EditScreenState>()
		.apply {
			viewModelScope.launch {
				value = wishId?.let {
					EditScreenState.InitialEditWish(getWish(wishId))
				} ?: EditScreenState.InitialNewWish
			}
		}

	override fun event(event: EditScreenEvent) {
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
		state.value = EditScreenState.Validating(nameField = nameField)
	}

	private fun EditScreenEvent.CostInputLostFocus.reduce() {
		state.value = EditScreenState.Validating(costField = costValidator(cost))
	}

	private fun EditScreenEvent.ImportanceInputLostFocus.reduce() {
		state.value = EditScreenState.Validating(importanceField = importanceValidator(importance))
	}

	private fun EditScreenEvent.NameInputReceiveFocus.reduce() {
		state.value = EditScreenState.Validating(nameField = true)
	}

	private fun EditScreenEvent.CostInputReceiveFocus.reduce() {
		state.value = EditScreenState.Validating(costField = true)
	}

	private fun EditScreenEvent.ImportanceInputReceiveFocus.reduce() {
		state.value = EditScreenState.Validating(importanceField = true)
	}

	//TODO осторожно, говнокод!! Перейти на MVI и выпилить это дерьмо
	private fun EditScreenEvent.ApplyClick.applyData() {
		val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
		val costField = costValidator(cost)
		val importanceField = importanceValidator(importance)

		state.value = EditScreenState.Validating(nameField, costField, importanceField)

		if (nameField && costField && importanceField) {
			val wish = Wish(name = name,
							info = info,
							cost = cost.toInt(),
							importance = Importance.valueOf(importance))

			viewModelScope.launch {
				wish.saving()
				state.value = EditScreenState.WishSaved
			}
		}
	}

	private suspend fun Wish.saving() = wishId?.let { editWishUseCase(copy(id = it)) }
		?: addWish(this)
}