android {

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

	implementation("androidx.appcompat:appcompat:1.3.0")

	kapt("androidx.room:room-compiler:2.3.0")
	implementation("androidx.room:room-runtime:2.3.0")
	implementation("androidx.room:room-ktx:2.3.0")

	implementation("org.koin:koin-core:2.1.5")
	implementation("org.koin:koin-core-ext:2.1.5")
	implementation("org.koin:koin-android:2.1.5")
}