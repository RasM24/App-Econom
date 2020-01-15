package ru.endroad.econom.feature.wishes.domain

class CostValidator {

	operator fun invoke(value: String): Boolean =
		runCatching { value.toInt() > 0 }.getOrDefault(false)
}