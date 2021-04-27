package ru.endroad.econom.feature.wishes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.endroad.composable.ActionText
import ru.endroad.composable.TitleText

@Composable
fun WishActionBottomSheet(
	title: String,
	onClickEdit: () -> Unit,
	onClickComplete: () -> Unit,
	onClickDelete: () -> Unit
) {
	Column {
		TitleText(
			modifier = Modifier
				.align(CenterHorizontally)
				.padding(16.dp),
			text = title
		)
		Divider()

		ActionIconText(onClick = onClickEdit, imageVector = Icons.Default.Create, contentDescription = "Изменить", text = "Изменить") //TODO hardcore text
		ActionIconText(onClick = onClickComplete, imageVector = Icons.Default.Done, contentDescription = "Выполнено", text = "Выполнено") //TODO hardcore text
		ActionIconText(onClick = onClickDelete, imageVector = Icons.Default.Clear, contentDescription = "Удалить", text = "Удалить") //TODO hardcore text
	}
}

@Composable
private fun ActionIconText(
	onClick: () -> Unit,
	imageVector: ImageVector,
	contentDescription: String?,
	text: String,
) = Box(
	modifier = Modifier
		.fillMaxWidth()
		.clickable(onClick = onClick)
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp)
	) {
		val modifier = Modifier.align(CenterVertically)
		Icon(modifier = modifier.padding(4.dp), imageVector = imageVector, contentDescription = contentDescription, tint = MaterialTheme.colors.primary)
		ActionText(modifier = modifier, text = text, color = MaterialTheme.colors.primary)
	}
}
//
//<item name="android:layout_width">match_parent</item>
//<item name="android:layout_height">wrap_content</item>
//<item name="iconPadding">8dp</item>
//<item name="android:layout_marginLeft">16dp</item>
//<item name="android:layout_marginRight">16dp</item>
//<item name="android:layout_marginTop">8dp</item>
//<item name="android:layout_marginBottom">8dp</item>
//<item name="android:gravity">left|center_vertical</item>