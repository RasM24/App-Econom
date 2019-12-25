package ru.endroad.econom.entity

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.endroad.econom.R
import ru.endroad.econom.data.room.WISH_TABLE

@Entity(tableName = WISH_TABLE) //TODO при необходимости разнести на сущность и pojo-модель
data class Wish(
	val name: String,
	val cost: Int,
	val importance: Importance = Importance.BACKLOG,
	val info: String = "",
	val complete: Boolean = false
) {

	@PrimaryKey(autoGenerate = true)
	var id: Int = 0

	val costInNoodles: Int get() = cost / NOODLE_COST

	val markerColor: Int get() = importance.colorId

	companion object {
		const val NOODLE_COST = 17
	}
}

enum class Importance(@IdRes val nameResId: Int, val colorId: Int) {
	CRITICAL(R.string.importance_critical, R.color.colorHighlighting),
	BIG(R.string.importance_big, R.color.colorImportant),
	LITTLE(R.string.importance_little, R.color.colorNoMatter),
	BACKLOG(R.string.importance_backlog, R.color.colorIndifference)
}