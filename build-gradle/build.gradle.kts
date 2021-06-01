//TODO непонятным образом работает таска clean, ругаясь на данный модуль
// либо все починиться само собой после перехода на kotlin 1.5.0 (так как буду переписывать некоторые части gradle-скриптов)
// либо разобраться, что же было сделано не так
plugins {
	`kotlin-dsl`
}

repositories {
	jcenter()
	google()
}

dependencies{
	implementation("com.android.tools.build:gradle:7.0.0-beta03")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}