plugins {
	id("com.android.library")
	kotlin("android")
}
dependencies {
	implementation(project(":shared-wish-core"))

	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")

	implementation("androidx.room:room-ktx:2.3.0")

	implementation("org.koin:koin-core:2.1.5")
	implementation("org.koin:koin-core-ext:2.1.5")
}