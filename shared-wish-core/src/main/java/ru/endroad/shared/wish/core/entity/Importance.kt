package ru.endroad.shared.wish.core.entity

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import ru.endroad.shared.wish.core.R

enum class Importance(@StringRes val nameResId: Int, @ColorRes val colorId: Int) {
	CRITICAL(R.string.importance_critical, R.color.colorHighlighting),
	BIG(R.string.importance_big, R.color.colorImportant),
	LITTLE(R.string.importance_little, R.color.colorNoMatter),
	BACKLOG(R.string.importance_backlog, R.color.colorIndifference)
}