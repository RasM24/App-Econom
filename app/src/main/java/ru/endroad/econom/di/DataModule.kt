package ru.endroad.econom.di

import org.koin.dsl.module
import org.koin.experimental.builder.single
import ru.endroad.econom.data.EstimationRepository
import ru.endroad.econom.data.WishRepository
import ru.endroad.econom.data.room.AppDatabase

val dataModule = module {

	single { AppDatabase.build(context = get()) }
	single { get<AppDatabase>().wishDao() }
	single { get<AppDatabase>().estimationDao() }

	//Repositories
	single<WishRepository>()
	single<EstimationRepository>()
}