package composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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