package ru.endroad.econom.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.endroad.econom.mock.fillWishTable

fun buildDatabase(context: Context): AppDatabase =
	Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
		.preload { fillWishTable() }
		.build()

fun <T : RoomDatabase> RoomDatabase.Builder<T>.preload(preloader: SupportSQLiteDatabase.() -> Unit): RoomDatabase.Builder<T> {
	this.addCallback(object : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			db.preloader()
		}
	})

	return this
}