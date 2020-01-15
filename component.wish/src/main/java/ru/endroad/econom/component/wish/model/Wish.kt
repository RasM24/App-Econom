package ru.endroad.econom.component.wish.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.endroad.econom.component.wish.datasource.WISH_TABLE

typealias WishList = List<Wish>

@Entity(tableName = WISH_TABLE) //TODO при необходимости разнести на сущность и pojo-модель
data class Wish(
	@PrimaryKey(autoGenerate = true) var id: Int = 0,
	val name: String,
	val cost: Int,
	val importance: Importance = Importance.BACKLOG,
	val info: String = "",
	val complete: Boolean = false
) {


	val costInNoodles: Int get() = cost / NOODLE_COST

	val markerColor: Int get() = importance.colorId

	companion object {
		const val NOODLE_COST = 17
	}
}