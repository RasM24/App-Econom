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

	flavorDimensions("mock")
	productFlavors {
		create("mock") {
			dimension = "mock"
			applicationIdSuffix = ".mock"
		}

		create("live") {
			dimension = "mock"
		}
	}
}

dependencies {
	androidTestImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.2")
	androidTestImplementation("com.android.support.test:rules:1.0.2")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
	androidTestImplementation("androidx.test.espresso:espresso-contrib:3.3.0")
	androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
	androidTestImplementation("androidx.test.espresso:espresso-intents:3.3.0")

	implementation(project(":feature-wish-active"))
	implementation(project(":feature-wish-completed"))
	implementation(project(":feature-wish-detail"))
	implementation(project(":shared-wish-datasource"))
	implementation(project(":component-compose-theme"))

	add("mockImplementation", project(":mock-wish"))
	implementation("com.facebook.stetho:stetho:1.5.1")

	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")

	implementation("androidx.appcompat:appcompat:1.3.0")
	implementation("androidx.activity:activity-compose:1.3.0-alpha07")

	kapt("androidx.room:room-compiler:2.3.0")
	implementation("androidx.room:room-runtime:2.3.0")
	implementation("androidx.room:room-ktx:2.3.0")

	implementation("org.koin:koin-core:2.1.5")
	implementation("org.koin:koin-core-ext:2.1.5")
	implementation("org.koin:koin-android:2.1.5")

	implementation("androidx.compose.ui:ui:1.0.0-beta06")
	implementation("androidx.compose.animation:animation:1.0.0-beta06")
}