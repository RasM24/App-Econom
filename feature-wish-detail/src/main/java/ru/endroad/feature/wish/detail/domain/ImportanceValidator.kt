package ru.endroad.feature.wish.detail.domain

import ru.endroad.shared.wish.core.entity.Importance

object ImportanceValidator {

	operator fun invoke(value: String): ValidationResult =
		runCatching { Importance.valueOf(value) }
			.fold(onSuccess = { ValidationResult.Valid },
				  onFailure = { ValidationResult.Invalid("Выберите важность") }) //TODO Hardcore Text
}
