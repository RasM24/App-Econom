package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenState

interface IWishEditViewModel {

	val state: LiveData<EditScreenState>
	fun event(event: EditScreenEvent)

	val stateW: StateW
}

sealed class StateW

object NewWishState : StateW()
class EditWishState(val wish: Deferred<Wish>) : StateW()

