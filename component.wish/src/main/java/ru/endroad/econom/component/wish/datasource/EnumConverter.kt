package ru.endroad.econom.component.wish.datasource

import androidx.room.TypeConverter
import ru.endroad.econom.component.wish.model.Importance

class EnumConverter {

	@TypeConverter
	fun fromEnum(value: Importance): String = value.name

	@TypeConverter
	fun toEnum(name: String): Importance = Importance.valueOf(name)
}
