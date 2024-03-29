package ru.endroad.econom.room

import android.content.Context
import androidx.room.Room

fun buildDatabase(context: Context): AppDatabase =
	Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
		.build()