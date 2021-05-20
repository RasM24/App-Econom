package ru.endroad.feature.wish.detail.presentation

import ru.endroad.shared.wish.core.entity.Wish

sealed class EditScreenState {

	object Initial : EditScreenState()
	object InitialNewWish : EditScreenState()
	class InitialEditWish(val wish: Wish) : EditScreenState()
}