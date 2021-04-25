package ru.endroad.feature.wish.detail.domain

object CostValidator {

	operator fun invoke(value: String): ValidationResult =
		runCatching { require(value.toInt() >= 0) }.fold(
			onSuccess = { ValidationResult.Valid },
			onFailure = { ValidationResult.Invalid("Введите ценник") }, //TODO HardcoreText
		)
}