package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.endroad.econom.component.wish.domain.AddWishUseCase
import ru.endroad.econom.component.wish.domain.EditWishUseCase
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.view.*

class WishEditViewModel(
	private val addWish: AddWishUseCase,
	private val editWishUseCase: EditWishUseCase,
	override val state: StateW
) : ViewModel(), IWishEditViewModel {

	override val validation: MutableLiveData<FieldsValidation> = MutableLiveData()

	override fun event(event: EditScreenEvent) {
		when (event) {
			is EditScreenEvent.Apply                    -> applyData(event)

			is EditScreenEvent.NameInputLostFocus       -> TODO()
			is EditScreenEvent.InfoInputLostFocus       -> TODO()
			is EditScreenEvent.CostInputLostFocus       -> TODO()
			is EditScreenEvent.ImportanceInputLostFocus -> TODO()
		}
	}

	//TODO осторожно, говнокод!! Перейти на MVI и выпилить это дерьмо
	private fun applyData(applyEvent: EditScreenEvent.Apply) {
		val validation = FieldsValidation.validate(applyEvent.name, applyEvent.cost, applyEvent.importance)

		this.validation.value = validation

		if (validation.validate) {
			val wish = Wish(
				name = applyEvent.name,
				info = applyEvent.info,
				cost = applyEvent.cost.toInt(),
				importance = Importance.valueOf(applyEvent.importance)
			)

			when (state) {
				NewWishState     -> addWish(wish)
				is EditWishState -> editWishUseCase(wish.apply { this.id = id }) //TODO передавать Id во вью модель
			}
		}
	}
}