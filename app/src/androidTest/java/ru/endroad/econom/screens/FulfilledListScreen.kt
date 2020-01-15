package ru.endroad.econom.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import ru.endroad.tavern.base.BaseScreen
import ru.endroad.tavern.base.KView

//TODO добавить аннотацию layout
object FulfilledListScreen : BaseScreen<FulfilledListScreen>() {

	val itemLexusWish = KView(withText("Автомобиль Lexus"))
}