package ru.endroad.econom

import ru.endroad.arena.viewlayer.activity.BaseActivity

class SingleActivity : BaseActivity() {

	override val layout: Int = R.layout.base_activity

	override val theme: Int = R.style.AppTheme

	override fun onFirstCreate() {}
}