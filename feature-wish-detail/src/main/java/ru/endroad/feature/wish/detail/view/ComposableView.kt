package ru.endroad.feature.wish.detail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import ru.endroad.composable.ActionText
import ru.endroad.composable.NavigationIcon
import ru.endroad.feature.wish.detail.domain.ValidationResult

@Composable
internal fun FlatTopBar(
	title: String,
	navigationClick: () -> Unit,
) = TopAppBar(
	title = { Text(text = title) },
	navigationIcon = { NavigationIcon(onClick = navigationClick) },
)

@Composable
internal fun ScrolledColumn(
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit
) {
	val scrollState = rememberScrollState()

	Column(
		modifier = modifier
			.fillMaxWidth()
			.verticalScroll(scrollState)
			.clipToBounds(),
		content = content,
	)
}

@Composable
internal fun PinnedButton(
	modifier: Modifier = Modifier,
	text: String,
	onClick: () -> Unit,
) = Box(modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)) {
	Button(modifier = modifier, onClick = onClick) {
		ActionText(text = text)
	}
}

@Composable
internal fun errorText(validationResult: ValidationResult.Invalid): @Composable (BoxScope.() -> Unit) =
	{ Text(text = validationResult.reason) }