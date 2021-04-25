package ru.endroad.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun GradientCard(
	modifier: Modifier = Modifier,
	markerColor: Color,
	content: @Composable () -> Unit
) {
	val shape = MaterialTheme.shapes.large
	val elevationPx = with(LocalDensity.current) { 4.dp.toPx() }
	val brush = Brush.horizontalGradient(listOf(markerColor, MaterialTheme.colors.surface))

	Box(
		modifier
			.graphicsLayer(shadowElevation = elevationPx, shape = shape, alpha = 0.99f) //TODO alpha = 0.99f для багфикса странного поведения
			.background(
				brush = brush,
				shape = shape
			)
			.clip(shape),
		propagateMinConstraints = true,
	) { content() }
}
