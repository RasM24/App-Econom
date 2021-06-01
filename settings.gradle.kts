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

pluginManagement {
	repositories {
		google()
		jcenter()
	}

	resolutionStrategy {
		eachPlugin {
			val pluginId = requested.id.id

			when {
				pluginId.startsWith("org.jetbrains.kotlin") -> useVersion("1.4.32")
				pluginId.startsWith("com.android.")         -> useModule("com.android.tools.build:gradle:7.0.0-alpha15")
			}
		}
	}
}

dependencyResolutionManagement {
	repositories {
		google()
		jcenter()
	}
}