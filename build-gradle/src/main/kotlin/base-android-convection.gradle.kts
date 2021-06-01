import com.android.build.gradle.BaseExtension

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