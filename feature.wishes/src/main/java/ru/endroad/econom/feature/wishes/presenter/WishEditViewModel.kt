package ru.endroad.econom.feature.wishes.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.endroad.econom.component.wish.domain.AddWishUseCase
import ru.endroad.econom.component.wish.domain.EditWishUseCase
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.view.*

class WishEditViewModel(
	private val addWish: AddWishUseCase,
	private val editWishUseCase: EditWishUseCase,
	override val state: StateW
) : ViewModel(), IWishEditViewModel {

	override val validation: MutableLiveData<FieldsValidation> = MutableLiveData()

	//TODO осторожно, говнокод!! Перейти на MVI и выпилить это дерьмо
	override fun applyData(name: String, cost: String, importance: String, info: String, id: Int) {
		val validation = FieldsValidation.validate(name, cost, importance)

		this.validation.value = validation

		if (validation.validate) {
			val wish = Wish(name = name,
							info = info,
							cost = cost.toInt(),
							importance = Importance.valueOf(importance))

			when (state) {
				NewWishState     -> addWish(wish)
				is EditWishState -> editWishUseCase(wish.apply { this.id = id })
			}
		}
	}
}