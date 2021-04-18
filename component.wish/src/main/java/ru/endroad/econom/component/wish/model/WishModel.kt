package ru.endroad.econom.component.wish.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.endroad.econom.component.wish.datasource.WISH_TABLE

//TODO придумать, как сделать internal
@Entity(tableName = WISH_TABLE)
data class WishModel(
	@PrimaryKey(autoGenerate = true) var id: Int = 0,
	val name: String,
	val cost: Int,
	val importance: String,
	val info: String = "",
	val complete: Boolean = false
)