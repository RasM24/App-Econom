package ru.endroad.econom.data.room

import androidx.room.TypeConverter
import ru.endroad.econom.entity.Importance

class EnumConverter {

	@TypeConverter
	fun fromEnum(value: Importance): String {
		return value.name
	}

	@TypeConverter
	fun toEnum(name: String): Importance {
		return Importance.valueOf(name)
	}
}
