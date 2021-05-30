import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin

buildscript {
	repositories {
		google()
		jcenter()

	}
	dependencies {
		classpath("com.android.tools.build:gradle:7.0.0-alpha15")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
	}
}

allprojects {
	repositories {
		google()
		mavenLocal()
		jcenter()
	}
}

subprojects {
	plugins.matching { it is AppPlugin || it is LibraryPlugin }.whenPluginAdded {
		configure<BaseExtension> {
			compileSdkVersion(30)

			defaultConfig {
				minSdk = 21
				targetSdk = 30
				versionCode = 1
				versionName = "0.0.1"

				testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
			}

			buildTypes {
				getByName("release") {
					isMinifyEnabled = false
					proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
				}
			}
		}
	}
}