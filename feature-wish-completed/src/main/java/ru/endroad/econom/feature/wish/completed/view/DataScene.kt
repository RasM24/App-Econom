package ru.endroad.econom.feature.wish.completed.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.endroad.shared.wish.core.entity.Wish

@Composable
fun DataScene(wishList: List<Wish>) {
	LazyColumn(modifier = Modifier.fillMaxSize()) {
		items(wishList, Wish::id) { WishCard(wish = it) }
	}
}