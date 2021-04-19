package ru.endroad.feature.wish.detail.domain

class CostValidator {

	operator fun invoke(value: String): Boolean =
		runCatching { value.toInt() > 0 }.getOrDefault(false)
}