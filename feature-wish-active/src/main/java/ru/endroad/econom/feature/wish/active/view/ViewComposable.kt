package ru.endroad.econom.feature.wish.active.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.endroad.composable.GradientCard
import ru.endroad.composable.PrimaryText
import ru.endroad.composable.SecondaryText
import ru.endroad.composable.TitleText
import ru.endroad.compose.theme.dark600
import ru.endroad.econom.feature.wish.active.R
import ru.endroad.shared.wish.core.domain.costInNoodles
import ru.endroad.shared.wish.core.domain.markerColor
import ru.endroad.shared.wish.core.entity.Wish

@Composable
internal fun FlatTopBar(actions: @Composable RowScope.() -> Unit) = TopAppBar(
	title = { Text(text = stringResource(id = R.string.wish_list_title)) },
	actions = { CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium, content = { actions() }) },
)

@Composable
internal fun MenuCompletedTaskActions(onClick: () -> Unit) =
	IconButton(onClick = onClick) {
		Icon(
			imageVector = Icons.Outlined.Task,
			contentDescription = stringResource(R.string.wish_list_menu_completed)
		)
	}

@Composable
internal fun MenuMockSettingActions(onClick: () -> Unit) =
	IconButton(onClick = onClick) {
		Icon(
			imageVector = Icons.Outlined.Settings,
			contentDescription = stringResource(R.string.wish_list_menu_completed)
		)
	}

@Composable
internal fun WishCard(wish: Wish, onClick: () -> Unit) =
	GradientCard(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		markerColor = wish.markerColor,
	) {
		Box(
			modifier = Modifier
				.wrapContentSize()
				.clickable(onClick = onClick)
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
	}

@Composable
internal fun AddFloatingActionButton(onClick: () -> Unit) =
	FloatingActionButton(onClick = onClick) {
		Icon(imageVector = Icons.Default.Add, contentDescription = "Add Wish")
	}