package ru.endroad.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.endroad.compose.theme.MaterialColor
import ru.endroad.compose.theme.s600

@Composable
fun TitleText(
	text: String,
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified,
) = Text(
	text = text,
	modifier = modifier,
	color = color,
	style = MaterialTheme.typography.h6,
)

@Composable
fun PrimaryText(
	text: String,
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified,
) = Text(
	text = text,
	modifier = modifier,
	color = color,
	style = MaterialTheme.typography.body1,
)

@Composable
fun SecondaryText(
	text: String,
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified,
) = Text(
	text = text,
	modifier = modifier,
	color = color,
	style = MaterialTheme.typography.body2,
)

@Composable
fun CaptionText(
	text: String,
	modifier: Modifier = Modifier,
	color: Color = MaterialColor.Gray.s600, //TODO вынести в кастомную палитру
) = Text(
	text = text,
	modifier = modifier,
	color = color,
	style = MaterialTheme.typography.caption,
)