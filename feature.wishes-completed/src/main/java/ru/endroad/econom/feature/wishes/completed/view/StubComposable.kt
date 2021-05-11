package ru.endroad.econom.feature.wishes.completed.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.endroad.composable.ImageStub
import ru.endroad.econom.feature.wishes.completed.R

@Composable
fun RenderNoCompletedStub() = ImageStub(
	painter = painterResource(id = R.drawable.empty),
	contentDescription = null,
	titleText = stringResource(id = R.string.noCompletedTitle),
	descriptionText = stringResource(id = R.string.noCompletedDescriptor),
)
