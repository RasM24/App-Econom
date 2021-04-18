package ru.endroad.econom.feature.wishes.completed.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import composable.GradientCard
import composable.PrimaryText
import composable.SecondaryText
import composable.TitleText
import ru.endroad.compose.theme.dark600
import ru.endroad.shared.wish.core.domain.costInNoodles
import ru.endroad.shared.wish.core.domain.markerColor
import ru.endroad.shared.wish.core.entity.Wish

@Composable
internal fun WishItem(wish: Wish) =
	GradientCard(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		markerColor = colorResource(id = wish.markerColor),
	) {
		Column(
			modifier = Modifier
				.padding(vertical = 24.dp, horizontal = 16.dp)
				.fillMaxWidth()
				.wrapContentHeight()
		) {
			TitleText(text = wish.name)
			Spacer(modifier = Modifier.height(8.dp))
			PrimaryText(text = wish.info)
			Spacer(modifier = Modifier.height(8.dp))
			PrimaryText(text = "${wish.cost}р", color = MaterialTheme.colors.primary, modifier = Modifier.align(Alignment.End))
			SecondaryText(text = "~${wish.costInNoodles} пачек лапши", color = MaterialTheme.colors.dark600, modifier = Modifier.align(Alignment.End))
		}
	}