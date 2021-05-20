package ru.endroad.shared.wish.core.domain

import androidx.compose.ui.graphics.Color
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

private const val NOODLE_COST = 17

val Wish.costInNoodles: Int get() = cost / NOODLE_COST

val Wish.markerColor: Color
	get() = when (importance) {
		Importance.CRITICAL -> colorHighlighting
		Importance.BIG      -> colorImportant
		Importance.LITTLE   -> colorNoMatter
		Importance.BACKLOG  -> colorIndifference
	}

//TODO сейчас цвета банально дублируют основную палитру
// необходимо:
// 1. Создать палитру для кастомных цветов, с поддержкой тем
// 2. Вынести данные цвета в палитру
private val colorHighlighting = Color(0xffFF8E58)
private val colorImportant = Color(0xff3EB48A)
private val colorNoMatter = Color(0x803EB48A)
private val colorIndifference = Color(0x26000000)