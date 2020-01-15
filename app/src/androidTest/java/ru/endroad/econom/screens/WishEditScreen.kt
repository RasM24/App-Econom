package ru.endroad.econom.screens

import androidx.test.espresso.matcher.ViewMatchers.*
import ru.endroad.econom.R
import ru.endroad.tavern.base.BaseScreen
import ru.endroad.tavern.base.KView

object WishEditScreen : BaseScreen<WishEditScreen>() {

	//val title = KView(TODO())

	val primaryTitle = KView(withId(R.id.textMainInfoBlock))
	val detailTitle = KView(withId(R.id.textDetailInfoBlock))

	val nameInput = KView(withId(R.id.input_name))
	val infoInput = KView(withId(R.id.input_info))
	val costInput = KView(withId(R.id.input_cost))
	val importanceInput = KView(withId(R.id.input_important))

	val nameInputLayout = KView(withId(R.id.input_name_layout))
	val infoInputLayout = KView(withId(R.id.input_info_layout))
	val costInputLayout = KView(withId(R.id.input_cost_layout))
	val importanceInputLayout = KView(withId(R.id.input_important_layout))

	val applyButton = KView(withId(R.id.apply))
}