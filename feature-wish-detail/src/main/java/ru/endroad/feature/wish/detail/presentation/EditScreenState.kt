package ru.endroad.feature.wish.detail.presentation

import ru.endroad.shared.wish.core.entity.Importance

sealed class EditScreenState {

	object Idle : EditScreenState()
	class DraftWish(
		val name: String?,
		val cost: Int?,
		val importance: Importance?,
		val info: String?,
	) : EditScreenState()
}