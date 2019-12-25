package ru.endroad.econom.component.wish

import androidx.annotation.ColorRes
import androidx.annotation.IdRes

enum class Importance(@IdRes val nameResId: Int, @ColorRes val colorId: Int) {
	CRITICAL(R.string.importance_critical, R.color.colorHighlighting),
	BIG(R.string.importance_big, R.color.colorImportant),
	LITTLE(R.string.importance_little, R.color.colorNoMatter),
	BACKLOG(R.string.importance_backlog, R.color.colorIndifference)
}