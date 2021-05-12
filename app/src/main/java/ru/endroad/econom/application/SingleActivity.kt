package ru.endroad.econom.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.endroad.component.core.Navigator
import ru.endroad.component.core.changeRoot
import ru.endroad.econom.R
import ru.endroad.econom.feature.navigation.AppBarFragment
import ru.endroad.econom.feature.wishes.view.WishListFragment

class SingleActivity : AppCompatActivity() {

	private val navigator: Navigator by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.base_activity)
		navigator.hubActivity = this

		if (savedInstanceState == null) {
			supportFragmentManager.changeRoot(AppBarFragment(), R.id.root)
			supportFragmentManager.changeRoot(WishListFragment(), R.id.content)
		}
	}


	override fun onDestroy() {
		navigator.hubActivity = null
		super.onDestroy()
	}
}