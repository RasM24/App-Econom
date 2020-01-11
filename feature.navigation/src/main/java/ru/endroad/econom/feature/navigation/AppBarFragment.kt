package ru.endroad.econom.feature.navigation

import android.content.Context.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_appbar.*
import ru.endroad.arena.viewlayer.fragment.BaseFragment

//TODO проверить и настроить такие кейсы:
// 1. detach этого фрагмента, нужно ли отвязывать toolbar
// 2. на некоторых экранах не нужен toolbar - придумать как лучше его скрывать
class AppBarFragment : BaseFragment() {

	override val layout = R.layout.activity_appbar

	override fun setupViewComponents() {
		(activity as AppCompatActivity).run {
			setSupportActionBar(toolbar)
			toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
			supportFragmentManager.addOnBackStackChangedListener {
				hideSoftKeyboard()
				supportActionBar?.setHomeEnabled()
			}
			supportActionBar?.setHomeEnabled()
		}
	}

	private fun ActionBar.setHomeEnabled() {
		this.setDisplayHomeAsUpEnabled(requireFragmentManager().backStackEntryCount != 0)
	}

	private fun AppCompatActivity.hideSoftKeyboard() {
		val inputMethodManager = (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager) ?: return
		currentFocus?.run { inputMethodManager.hideSoftInputFromWindow(windowToken, 0) }
	}
}