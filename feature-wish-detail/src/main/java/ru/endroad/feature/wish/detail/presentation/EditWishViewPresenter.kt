package ru.endroad.feature.wish.detail.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.endroad.component.core.PresenterMviAbstract
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

//TODO требуется пересмотр подходов в сязи с внедрением JetpackCompose
class EditWishViewPresenter(
	private val wishId: Int?,
	private val getWish: GetWishUseCase,
	private val addWish: AddWishUseCase,
	private val editWish: EditWishUseCase,
	private val router: WishDetailRouter
) : PresenterMviAbstract<EditScreenState>() {

	override val state: MutableStateFlow<EditScreenState> = MutableStateFlow(EditScreenState.Initial)

	init {
		loadDraft()
	}

	private fun loadDraft() {
		CoroutineScope(Dispatchers.Main).launch {
			val wish = wishId?.let { getWish(it) }
			val newState = wish?.let(EditScreenState::InitialEditWish) ?: EditScreenState.InitialNewWish
			newState.applyState()
		}
	}

	fun saveWish(name: String, cost: String, importance: String, info: String) {
		CoroutineScope(Dispatchers.Main).launch {
			val wish = Wish(name = name, info = info, cost = cost.toInt(), importance = Importance.valueOf(importance))

			wishId?.let { editWish(wish.copy(id = it)) } ?: addWish(wish)
			router.close()
		}
	}

	fun back() {
		router.close()
	}
}