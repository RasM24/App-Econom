includeBuild("build-gradle")

include(
	":app",
	":mock-wish",
	":feature-wish-active",
	":feature-wish-completed",
	":feature-wish-detail",
	":shared-wish-core",
	":shared-wish-datasource",
	":component-compose-theme",
	":component-room"
)

dependencyResolutionManagement {
	repositories {
		google()
		jcenter()
	}
}