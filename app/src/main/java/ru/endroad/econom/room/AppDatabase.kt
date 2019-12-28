package ru.endroad.econom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.endroad.econom.component.wish.datasource.EnumConverter
import ru.endroad.econom.component.wish.datasource.WishDao
import ru.endroad.econom.component.wish.model.Wish

const val DATABASE_NAME = "CoreDatabase"

@Database(
	version = 1,
	entities = [
		Wish::class
	]
)
@TypeConverters(EnumConverter::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun wishDao(): WishDao

	companion object {
		fun build(context: Context): AppDatabase =
			Room.databaseBuilder(context, AppDatabase::class.java,
								 DATABASE_NAME)
				.build()
	}
}