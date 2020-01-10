package ru.endroad.econom.application

import android.os.Bundle
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import ru.endroad.arena.viewlayer.activity.BaseActivity
import ru.endroad.econom.R
import ru.endroad.econom.feature.navigation.AppBarFragment
import ru.endroad.econom.feature.wishes.view.WishListFragment
import ru.endroad.navigation.changeRoot

class SingleActivity : BaseActivity() {

	private val fragmentManagerModule = module { factory { supportFragmentManager } }

	override val layout: Int = R.layout.base_activity

	override val theme: Int = R.style.AppTheme

	override fun onFirstCreate() {
		supportFragmentManager.changeRoot(AppBarFragment(), R.id.root)
		supportFragmentManager.changeRoot(WishListFragment.getInstance(), R.id.content)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		loadKoinModules(fragmentManagerModule)
		super.onCreate(savedInstanceState)
	}

	override fun onDestroy() {
		unloadKoinModules(fragmentManagerModule)
		super.onDestroy()
	}
}