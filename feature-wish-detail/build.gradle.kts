plugins {
	id("com.android.library")
	kotlin("android")
}
android {
	//region Experimental. Удалить после релиза Jetpack Compose и перехода на kotlin 1.5.0
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
		useIR = true
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.0.0-beta06"
	}
	//endregion
}

dependencies {
	implementation(project(":shared-wish-core"))
	implementation(project(":component-compose-theme"))

	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

	implementation("org.koin:koin-core:2.1.5")

	implementation("androidx.compose.ui:ui:1.0.0-beta06")
	implementation("androidx.compose.foundation:foundation:1.0.0-beta06")
	implementation("androidx.compose.material:material:1.0.0-beta06")
}