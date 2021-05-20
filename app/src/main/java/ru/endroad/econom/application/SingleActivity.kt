package ru.endroad.econom.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.endroad.component.core.Navigator
import ru.endroad.econom.R

class SingleActivity : AppCompatActivity() {

	private val navigator: Navigator by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.base_activity)
		navigator.hubActivity = this
	}


	override fun onDestroy() {
		navigator.hubActivity = null
		super.onDestroy()
	}
}