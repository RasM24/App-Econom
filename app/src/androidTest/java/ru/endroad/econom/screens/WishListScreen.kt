package ru.endroad.econom.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import ru.endroad.econom.R
import ru.endroad.tavern.base.BaseScreen
import ru.endroad.tavern.base.KView
import ru.endroad.tavern.base.Layout

@Layout(R.layout.wish_fragment_list)
object WishListScreen : BaseScreen<WishListScreen>() {

	val wishList = KView(withId(R.id.list))

	val newWishFab = KView(withId(R.id.new_wish))
	val fulfilledList = KView(withId(R.id.menu_completed))

	val itemLexusWish = KView(withText("Автомобиль Lexus"))
}