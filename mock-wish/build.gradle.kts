//TODO Избавится от зависимости на Android
plugins {
	id("android-library-convection")
}
dependencies {
	implementation(project(":component-room"))
	implementation(project(":shared-wish-datasource"))

	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")

	implementation("androidx.room:room-ktx:2.3.0")
}