package ru.endroad.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OneActionStub(
	imageVector: ImageVector,
	contentDescription: String?,
	titleText: String,
	descriptionText: String? = null,
	mainButtonText: String,
	doTheMainAction: () -> Unit,
) = Stub(
	modifier = Modifier.fillMaxSize(),
	imageVector = imageVector,
	contentDescription = contentDescription,
	titleText = titleText,
	descriptionText = descriptionText,
	mainButtonText = mainButtonText,
	secondaryButtonText = null,
	doTheMainAction = doTheMainAction,
)

@Composable
fun TwoActionStub(
	imageVector: ImageVector,
	contentDescription: String?,
	titleText: String,
	descriptionText: String? = null,
	mainButtonText: String,
	secondaryButtonText: String,
	doTheMainAction: () -> Unit,
	doTheSecondaryAction: () -> Unit,
) = Stub(
	modifier = Modifier.fillMaxSize(),
	imageVector = imageVector,
	contentDescription = contentDescription,
	titleText = titleText,
	descriptionText = descriptionText,
	mainButtonText = mainButtonText,
	secondaryButtonText = secondaryButtonText,
	doTheMainAction = doTheMainAction,
	doTheSecondaryAction = doTheSecondaryAction,
)

@Composable
internal fun Stub(
	modifier: Modifier,
	imageVector: ImageVector,
	contentDescription: String?,
	titleText: String,
	descriptionText: String?,
	mainButtonText: String?,
	secondaryButtonText: String?,
	doTheMainAction: () -> Unit = {},
	doTheSecondaryAction: () -> Unit = {},
) = Column(
	modifier = modifier,
	verticalArrangement = Arrangement.Center
) {
	val baseModifier = Modifier.align(CenterHorizontally)

	val imageModifier = baseModifier.fillMaxWidth(fraction = 0.5f)
	val buttonModifier = baseModifier.padding(horizontal = 16.dp, vertical = 4.dp)
	val textModifier = baseModifier.padding(horizontal = 16.dp, vertical = 4.dp)

	Image(modifier = imageModifier, imageVector = imageVector, contentDescription = contentDescription)

	Hole(size = 16.dp)
	TitleText(modifier = textModifier, text = titleText, textAlign = TextAlign.Center)
	descriptionText?.let { PrimaryText(modifier = textModifier, text = it, textAlign = TextAlign.Center) }

	Hole(size = 24.dp)
	secondaryButtonText?.let { OutlinedTextButton(modifier = buttonModifier, text = it, onClick = doTheSecondaryAction) }
	mainButtonText?.let { UnelevatedTextButton(modifier = buttonModifier, text = it, onClick = doTheMainAction) }
}

@Composable
@Deprecated("use stub with vectorImage")
fun OneActionStub(
	painter: Painter,
	contentDescription: String?,
	titleText: String,
	descriptionText: String? = null,
	mainButtonText: String,
	doTheMainAction: () -> Unit,
) = Stub(
	modifier = Modifier.fillMaxSize(),
	painter = painter,
	contentDescription = contentDescription,
	titleText = titleText,
	descriptionText = descriptionText,
	mainButtonText = mainButtonText,
	secondaryButtonText = null,
	doTheMainAction = doTheMainAction,
)

@Composable
@Deprecated("use stub with vectorImage")
fun TwoActionStub(
	painter: Painter,
	contentDescription: String?,
	titleText: String,
	descriptionText: String? = null,
	mainButtonText: String?,
	secondaryButtonText: String?,
	doTheMainAction: () -> Unit,
	doTheSecondaryAction: () -> Unit,
) = Stub(
	modifier = Modifier.fillMaxSize(),
	painter = painter,
	contentDescription = contentDescription,
	titleText = titleText,
	descriptionText = descriptionText,
	mainButtonText = mainButtonText,
	secondaryButtonText = secondaryButtonText,
	doTheMainAction = doTheMainAction,
	doTheSecondaryAction = doTheSecondaryAction,
)

@Composable
@Deprecated("use stub with vectorImage")
internal fun Stub(
	modifier: Modifier,
	painter: Painter,
	contentDescription: String?,
	titleText: String,
	descriptionText: String?,
	mainButtonText: String?,
	secondaryButtonText: String?,
	doTheMainAction: () -> Unit = {},
	doTheSecondaryAction: () -> Unit = {},
) = Column(
	modifier = modifier,
	verticalArrangement = Arrangement.Center
) {
	val baseModifier = Modifier.align(CenterHorizontally)

	val imageModifier = baseModifier.fillMaxWidth(fraction = 0.5f)
	val buttonModifier = baseModifier.padding(horizontal = 16.dp, vertical = 4.dp)
	val textModifier = baseModifier.padding(horizontal = 16.dp, vertical = 4.dp)

	Image(modifier = imageModifier, painter = painter, contentDescription = contentDescription)

	Hole(size = 16.dp)
	TitleText(modifier = textModifier, text = titleText, textAlign = TextAlign.Center)
	descriptionText?.let { PrimaryText(modifier = textModifier, text = it, textAlign = TextAlign.Center) }

	Hole(size = 24.dp)
	secondaryButtonText?.let { OutlinedTextButton(modifier = buttonModifier, text = it, onClick = doTheSecondaryAction) }
	mainButtonText?.let { UnelevatedTextButton(modifier = buttonModifier, text = it, onClick = doTheMainAction) }
}