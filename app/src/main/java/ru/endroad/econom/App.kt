package ru.endroad.econom

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.endroad.birusa.feature.estimation.featureEstimationModule
import ru.endroad.econom.component.wish.componentWishModule
import ru.endroad.econom.di.dataModule
import ru.endroad.econom.di.presenterModule

class App : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@App)
			modules(
				featureEstimationModule,
				componentWishModule,
				presenterModule,
				dataModule
			)
		}
	}
}