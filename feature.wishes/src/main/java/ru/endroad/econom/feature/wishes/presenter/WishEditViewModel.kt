package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.endroad.econom.component.wish.domain.AddWishUseCase
import ru.endroad.econom.component.wish.domain.EditWishUseCase
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.domain.CostValidator
import ru.endroad.econom.feature.wishes.domain.ImportanceValidator
import ru.endroad.econom.feature.wishes.domain.NameValidator
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenState
import ru.endroad.econom.feature.wishes.view.IWishEditViewModel
import ru.endroad.econom.feature.wishes.view.StateW

class WishEditViewModel(
	private val addWish: AddWishUseCase,
	private val editWishUseCase: EditWishUseCase,
	private val nameValidator: NameValidator,
	private val costValidator: CostValidator,
	private val importanceValidator: ImportanceValidator,
	override val stateW: StateW
) : ViewModel(), IWishEditViewModel {

	override val state = MutableLiveData<EditScreenState>()

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
			val wish = Wish(
				name = applyEvent.name,
				info = applyEvent.info,
				cost = applyEvent.cost.toInt(),
				importance = Importance.valueOf(applyEvent.importance)
			)

			//			when (state) {
			//				NewWishState     -> addWish(wish)
			//				is EditWishState -> editWishUseCase(wish.apply { this.id = id }) //TODO передавать Id во вью модель
			//			}
		}
	}
}