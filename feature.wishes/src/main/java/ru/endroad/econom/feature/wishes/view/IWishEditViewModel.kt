package ru.endroad.econom.feature.wishes.view

import androidx.lifecycle.LiveData
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenState

interface IWishEditViewModel {

	val state: LiveData<EditScreenState>
	fun event(event: EditScreenEvent)
}

