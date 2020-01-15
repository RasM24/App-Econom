package ru.endroad.econom.room

import org.koin.dsl.module

val dataModule = module {

	single { buildDatabase(context = get()) }
	single { get<AppDatabase>().wishDao() }
}