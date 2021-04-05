package ru.endroad.econom.feature.navigation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_appbar.*

//TODO проверить и настроить такие кейсы:
// 1. detach этого фрагмента, нужно ли отвязывать toolbar
// 2. на некоторых экранах не нужен toolbar - придумать как лучше его скрывать
class AppBarFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.activity_appbar, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

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