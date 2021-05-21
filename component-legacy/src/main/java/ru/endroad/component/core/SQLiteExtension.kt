package ru.endroad.component.core

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Deprecated("Вынести")
fun <T : RoomDatabase> RoomDatabase.Builder<T>.preload(preloader: SupportSQLiteDatabase.() -> Unit): RoomDatabase.Builder<T> {
	this.addCallback(object : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			db.preloader()
		}
	})

	return this
}