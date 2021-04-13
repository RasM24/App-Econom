package ru.endroad.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) = MaterialTheme(
	colors = if (darkTheme) DarkThemeColors else LightThemeColors,
	shapes = Shapes,
	content = content
)