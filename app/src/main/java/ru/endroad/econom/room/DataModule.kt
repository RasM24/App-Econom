package ru.endroad.econom.room

import org.koin.dsl.module
import ru.endroad.econom.room.AppDatabase

val dataModule = module {

	single { AppDatabase.build(context = get()) }
	single { get<AppDatabase>().wishDao() }
}