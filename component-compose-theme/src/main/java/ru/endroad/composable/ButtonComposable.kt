package ru.endroad.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun OutlinedTextButton(
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	elevation: ButtonElevation? = null,
	shape: Shape = MaterialTheme.shapes.small,
	border: BorderStroke? = ButtonDefaults.outlinedBorder,
	colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
	contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) = OutlinedButton(
	onClick = onClick,
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	elevation = elevation,
	shape = shape,
	border = border,
	colors = colors,
	contentPadding = contentPadding,
	content = { Text(text = text) }
)

@Composable
fun UnelevatedTextButton(
	text: String,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	elevation: ButtonElevation? = null,
	shape: Shape = MaterialTheme.shapes.small,
	border: BorderStroke? = null,
	colors: ButtonColors = ButtonDefaults.buttonColors(),
	contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) = Button(
	onClick = onClick,
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	elevation = elevation,
	shape = shape,
	border = border,
	colors = colors,
	contentPadding = contentPadding,
	content = { ActionText(text = text) }
)

@Composable
fun NavigationIcon(onClick: () -> Unit) {
	IconButton(onClick = onClick) {
		Icon(
			imageVector = Icons.Filled.ArrowBack,
			contentDescription = "Navigation"
		)
	}
}