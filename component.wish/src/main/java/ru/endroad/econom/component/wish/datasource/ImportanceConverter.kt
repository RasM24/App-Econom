package ru.endroad.econom.component.wish.datasource

import ru.endroad.shared.wish.core.entity.Importance

internal class ImportanceConverter {

	fun to(name: String): Importance = Importance.valueOf(name)
	fun from(value: Importance): String = value.name
}
