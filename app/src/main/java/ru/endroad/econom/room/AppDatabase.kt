package ru.endroad.econom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.endroad.econom.component.wish.datasource.WishDao
import ru.endroad.econom.component.wish.model.WishModel

const val DATABASE_NAME = "CoreDatabase"

@Database(
	version = 1,
	entities = [
		WishModel::class
	]
)
abstract class AppDatabase : RoomDatabase() {

	abstract fun wishDao(): WishDao
}