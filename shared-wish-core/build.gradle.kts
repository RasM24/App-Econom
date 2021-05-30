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
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")
	implementation("androidx.core:core-ktx:1.3.2")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

	//TODO Избавится от зависимости на compose
	implementation("androidx.compose.ui:ui:1.0.0-beta06")
}