package ru.endroad.feature.wish.detail.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.shared.wish.core.domain.AddWishUseCase
import ru.endroad.shared.wish.core.domain.EditWishUseCase
import ru.endroad.shared.wish.core.domain.GetWishUseCase
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

class EditWishActor(
	private val wishId: Int?
) {

	private val getWishUseCase by inject(GetWishUseCase::class.java)
	private val addWishUseCase by inject(AddWishUseCase::class.java)
	private val editWishUseCase by inject(EditWishUseCase::class.java)
	private val router by inject(WishDetailRouter::class.java)

	private val _state = MutableStateFlow<EditScreenState>(EditScreenState.Idle)
	val state: StateFlow<EditScreenState> = _state.asStateFlow()

	suspend fun loadData() {
		val wish = wishId?.let { getWishUseCase(it) }

		EditScreenState.DraftWish(
			name = wish?.name,
			cost = wish?.cost,
			importance = wish?.importance,
			info = wish?.info,
		)
			.let { _state.emit(it) }
	}

	suspend fun saveWish(name: String, cost: String, importance: String, info: String) {
		val wish = Wish(name = name, info = info, cost = cost.toInt(), importance = Importance.valueOf(importance))

		wishId?.let { editWishUseCase(wish.copy(id = it)) } ?: addWishUseCase(wish)
		router.close()
	}

	fun close() {
		router.close()
	}
}