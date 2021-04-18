package ru.endroad.shared.wish.core.entity

import androidx.annotation.StringRes
import ru.endroad.shared.wish.core.R

enum class Importance(@StringRes val nameResId: Int) {
	CRITICAL(R.string.importance_critical),
	BIG(R.string.importance_big),
	LITTLE(R.string.importance_little),
	BACKLOG(R.string.importance_backlog),
}