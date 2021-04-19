package ru.endroad.feature.wish.detail.domain

import ru.endroad.shared.wish.core.entity.Importance

class ImportanceValidator {

	operator fun invoke(value: String): Boolean =
		runCatching { Importance.valueOf(value) }
			.fold({ true }, { false })
}
