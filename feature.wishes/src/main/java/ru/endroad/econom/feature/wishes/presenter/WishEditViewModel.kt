package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
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

class WishEditViewModel(
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
			is EditScreenEvent.ApplyClick                 -> applyData(event)

			is EditScreenEvent.NameInputChangeFocus       -> event.reduce()
			is EditScreenEvent.InfoInputChangeFocus       -> Unit
			is EditScreenEvent.CostInputChangeFocus       -> event.reduce()
			is EditScreenEvent.ImportanceInputChangeFocus -> event.reduce()
		}
	}

	private fun EditScreenEvent.NameInputChangeFocus.reduce() {
		if (hasFocus)
			state.value = EditScreenState.Validating(nameField = true)
		else {
			val nameField = nameValidator.isNotEmpty(name) && nameValidator.isNotLong(name)
			state.value = EditScreenState.Validating(nameField = nameField)
		}
	}

	private fun EditScreenEvent.CostInputChangeFocus.reduce() {
		if (hasFocus)
			state.value = EditScreenState.Validating(costField = true)
		else {
			state.value = EditScreenState.Validating(costField = costValidator(cost))
		}
	}

	private fun EditScreenEvent.ImportanceInputChangeFocus.reduce() {
		if (hasFocus)
			state.value = EditScreenState.Validating(importanceField = true)
		else {
			state.value = EditScreenState.Validating(importanceField = importanceValidator(importance))
		}
	}

	//TODO осторожно, говнокод!! Перейти на MVI и выпилить это дерьмо
	private fun applyData(applyEvent: EditScreenEvent.ApplyClick) {
		val nameField = nameValidator.isNotEmpty(applyEvent.name) && nameValidator.isNotLong(applyEvent.name)
		val costField = costValidator(applyEvent.cost)
		val importanceField = importanceValidator(applyEvent.importance)

		state.value = EditScreenState.Validating(nameField, costField, importanceField)

		if (nameField && costField && importanceField) {
			val wish = Wish(name = applyEvent.name,
							info = applyEvent.info,
							cost = applyEvent.cost.toInt(),
							importance = Importance.valueOf(applyEvent.importance))

			wish.update().invokeOnCompletion {
				//TODO добавить закрытие экрана
			}

		}
	}

	private fun Wish.update(): Job = wishId?.let { editWishUseCase(copy(id = it)) }
		?: addWish(this)
}