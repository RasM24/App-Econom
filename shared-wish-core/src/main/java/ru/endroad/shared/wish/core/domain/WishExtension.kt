package ru.endroad.shared.wish.core.domain

import ru.endroad.shared.wish.core.R
import ru.endroad.shared.wish.core.entity.Importance
import ru.endroad.shared.wish.core.entity.Wish

private const val NOODLE_COST = 17

val Wish.costInNoodles: Int get() = cost / NOODLE_COST

val Wish.markerColor: Int
	get() = when (importance) {
		Importance.CRITICAL -> R.color.colorHighlighting
		Importance.BIG      -> R.color.colorImportant
		Importance.LITTLE   -> R.color.colorNoMatter
		Importance.BACKLOG  -> R.color.colorIndifference
	}