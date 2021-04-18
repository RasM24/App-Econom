package ru.endroad.shared.wish.core.domain

private const val NOODLE_COST = 17

val costInNoodles: Int get() = cost / NOODLE_COST

val markerColor: Int get() = importance.colorId