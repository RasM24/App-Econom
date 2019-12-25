package ru.endroad.econom.di

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.endroad.econom.data.EstimationRepositoryImpl
import ru.endroad.econom.data.WishRepositoryImpl
import ru.endroad.econom.data.room.AppDatabase
import ru.endroad.econom.domain.EstimationRepository
import ru.endroad.econom.domain.WishRepository

val dataModule = module {

	single { AppDatabase.build(context = get()) }
	single { get<AppDatabase>().wishDao() }
	single { get<AppDatabase>().estimationDao() }

	//Repositories
	singleBy<WishRepository, WishRepositoryImpl>()
	singleBy<EstimationRepository, EstimationRepositoryImpl>()
}