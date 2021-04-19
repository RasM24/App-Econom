package ru.endroad.compose.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

//TODO Продумать дизайн-систему
private val Red200 = Color(0xfff297a2)
private val Red800 = Color(0xffd00036)
private val colorPrimary = Color(0xff3EB48A)
private val colorPrimaryDark = Color(0xff147553)
private val colorHighlighting = Color(0xffFF8E58)

//TODO разобраться с цветами
private val colorImportant = Color(0xff3EB48A)
private val colorNoMatter = Color(0x803EB48A)
private val colorIndifference = Color(0x26000000)
private val titleColor = Color(0xffDDDDDD)
private val dividerMainColor = Color(0xffEEEEEE)
private val dividerShadowColor = Color(0xffAAAAAA)
private val colorMint = Color(0xff8BDABE)
private val estimation_background = Color(0xff458770)

val Colors.dark600 get() = MaterialColor.Gray.s600

internal val LightThemeColors = lightColors(
	primary = colorPrimary,
	primaryVariant = colorPrimaryDark,
	onPrimary = Color.White,
	secondary = colorHighlighting,
	secondaryVariant = colorHighlighting,
	onSecondary = Color.White,
	error = MaterialColor.Red.alternative400
)

internal val DarkThemeColors = darkColors(
	primary = colorPrimary,
	primaryVariant = colorPrimaryDark,
	onPrimary = Color.Black,
	secondary = colorHighlighting,
	onSecondary = Color.White,
	error = MaterialColor.Red.alternative100
)