package ru.endroad.econom.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import ru.endroad.econom.R
import ru.endroad.tavern.base.BaseScreen
import ru.endroad.tavern.base.KView

object BottomSheetScreen : BaseScreen<BottomSheetScreen>() {
	val performButton = KView(withId(R.id.complete))
	val editButton = KView(withId(R.id.edit))
	val deleteButton = KView(withId(R.id.delete))
}