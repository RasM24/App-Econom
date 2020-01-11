package ru.endroad.econom.feature.wishes.domain

import ru.endroad.econom.component.wish.model.Importance

class ImportanceValidator {

	operator fun invoke(value: String): Boolean =
		runCatching { Importance.valueOf(value) }
			.fold({ true }, { false })
}
