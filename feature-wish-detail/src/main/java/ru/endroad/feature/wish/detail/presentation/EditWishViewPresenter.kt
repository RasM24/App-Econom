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
) : PresenterMviAbstract<EditScreenState, EditScreenEvent>() {

	override val state: MutableStateFlow<EditScreenState> = MutableStateFlow(EditScreenState.Initial)

	init {
		reduce(EditScreenEvent.LoadDraft)
	}

	override fun reduce(event: EditScreenEvent) {
		when (event) {
			is EditScreenEvent.LoadDraft  -> loadDraft()
			is EditScreenEvent.ApplyClick -> event.reduceAndApply()
		}
	}

	private fun loadDraft() {
		CoroutineScope(Dispatchers.Main).launch {
			val wish = wishId?.let { getWish(it) }
			val newState = wish?.let(EditScreenState::InitialEditWish) ?: EditScreenState.InitialNewWish
			newState.applyState()
		}
	}

	private fun EditScreenEvent.ApplyClick.reduceAndApply() {
		CoroutineScope(Dispatchers.Main).launch { saveWish(name, info, cost.toInt(), Importance.valueOf(importance)).applyState() }
	}

	//TODO можно вынести в domain
	private suspend fun saveWish(name: String, info: String, cost: Int, importance: Importance): EditScreenState {
		val wish = Wish(name = name, info = info, cost = cost, importance = importance)
		wishId?.let { editWish(wish.copy(id = it)) } ?: addWish(wish)

		return EditScreenState.WishSaved
	}
}