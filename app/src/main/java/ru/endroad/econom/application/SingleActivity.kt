package ru.endroad.econom.application

import ru.endroad.arena.viewlayer.activity.AppBarActivity
import ru.endroad.arena.viewlayer.activity.BaseActivity
import ru.endroad.econom.R
import ru.endroad.econom.feature.navigation.AppBarFragment
import ru.endroad.econom.feature.wishes.view.WishListFragment
import ru.endroad.navigation.changeRoot

class SingleActivity : BaseActivity() {

	override val layout: Int = R.layout.base_activity

	override val theme: Int = R.style.AppTheme

	override fun onFirstCreate() {
		supportFragmentManager.changeRoot(AppBarFragment(), R.id.root)
		supportFragmentManager.changeRoot(WishListFragment.getInstance(), R.id.content)
	}
}