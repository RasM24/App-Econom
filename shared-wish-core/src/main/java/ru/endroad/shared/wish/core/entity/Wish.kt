package ru.endroad.shared.wish.core.entity

data class Wish(
	val id: Int = 0,
	val name: String,
	val cost: Int,
	val importance: Importance = Importance.BACKLOG,
	val info: String = "",
	val complete: Boolean = false
)