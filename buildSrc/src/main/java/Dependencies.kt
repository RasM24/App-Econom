@file:Suppress("unused")

private const val kotlinVersion = "1.3.50"
private const val gradleVersion = "3.5.0"
private const val coroutineVersion = "1.2.1"
private const val fastadapterVersion = "3.3.1"
private const val materialVersion = "1.0.0"
private const val recycleViewVersion = "1.0.0"
private const val appcompatVersion = "1.1.0"
private const val androidxCoreVersion = "1.1.0"
private const val androidxViewModelVersion = "2.1.0"
private const val lifecycleExtensionVersion = "1.0.0"
private const val constraintLayoutVersion = "1.1.3"
private const val campModuleVersion = "0.3.0"
private const val gsonVersion = "2.8.5"
private const val picassoVersion = "2.71828"
private const val koinVersion = "2.0.0-rc-2"

object Classpathes {
	const val buildGradle = "com.android.tools.build:gradle:$gradleVersion"
	const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
	const val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVersion}"
}

object LibrariesCore {
	const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
	const val androidxAppcompat = "androidx.appcompat:appcompat:$appcompatVersion"
	const val androidxCoreKtx = "androidx.core:core-ktx:$androidxCoreVersion"
	const val androidxLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$androidxViewModelVersion"
	const val lifecycleExtension = "android.arch.lifecycle:extensions:$lifecycleExtensionVersion"
}

object LibrariesUI {
	const val material = "com.google.android.material:material:$materialVersion"
	const val recycleView = "androidx.recyclerview:recyclerview:$recycleViewVersion"
	const val fastadapterCore = "com.mikepenz:fastadapter:$fastadapterVersion"
	const val fastadapterCommons = "com.mikepenz:fastadapter-commons:$fastadapterVersion"
	const val fastadapterExtension = "com.mikepenz:fastadapter-extensions:$fastadapterVersion"
	const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
}

object LibrariesUtils {
	const val gson = "com.google.code.gson:gson:$gsonVersion"
	const val picasso = "com.squareup.picasso:picasso:$picassoVersion"
}

object LibrariesFramework {
	const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
	const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"

	const val koinCore = "org.koin:koin-core:$koinVersion"
	const val koinExtension = "org.koin:koin-core-ext:$koinVersion"
	const val koinViewModel = "org.koin:koin-androidx-viewmodel:$koinVersion"
}

object Modules {
	const val viewLayer = "ru.endroad.camp:base-viewlayer:$campModuleVersion"
	const val presentationLayer = "ru.endroad.camp:base-presentationlayer:$campModuleVersion"
	const val dataLayer = "ru.endroad.camp:base-datalayer:$campModuleVersion"
	const val coreMVI = "ru.endroad.camp:base-mvi:$campModuleVersion"
}

object Libraries {
	const val vkAuth = "com.vk:androidsdk:1.6.5"
	const val fbAuth = "com.facebook.android:facebook-login:5.0.0"
	const val googleAuth = "com.google.android.gms:play-services-auth:17.0.0"
}