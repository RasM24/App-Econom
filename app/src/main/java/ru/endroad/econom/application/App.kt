package ru.endroad.econom.application

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.endroad.birusa.feature.estimation.featureEstimationModule
import ru.endroad.econom.component.wish.componentWishModule
import ru.endroad.econom.feature.wishes.completed.di.featureCompletedWishesModule
import ru.endroad.econom.feature.wishes.di.featureWishesModule
import ru.endroad.econom.room.dataModule
import ru.endroad.econom.router.routerModule

class App : Application() {

	override fun onCreate() {
		super.onCreate()

		Stetho.initializeWithDefaults(this)

		startKoin {
			androidContext(this@App)
			modules(
				featureEstimationModule,
				featureWishesModule,
				featureCompletedWishesModule,
				componentWishModule,
				routerModule,
				dataModule
			)
		}
	}
}