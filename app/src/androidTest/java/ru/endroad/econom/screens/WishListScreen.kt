package ru.endroad.econom.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import ru.endroad.econom.R
import ru.endroad.tavern.base.BaseScreen
import ru.endroad.tavern.base.KView
import ru.endroad.tavern.base.Layout

@Layout(R.layout.wish_fragment_list)
object WishListScreen : BaseScreen<WishListScreen>() {

	val floatingButton = KView(withId(R.id.fab))

	val itemLexusWish = KView(withText("Автомобиль Lexus"))
}