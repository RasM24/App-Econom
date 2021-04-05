package ru.endroad.component.core

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

class ContentNavigator(@IdRes override val container: Int) : Navigator {

	override var hubActivity: AppCompatActivity? = null
}