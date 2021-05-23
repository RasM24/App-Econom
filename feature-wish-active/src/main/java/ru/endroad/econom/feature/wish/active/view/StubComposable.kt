package ru.endroad.econom.feature.wish.active.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.endroad.composable.OneActionStub
import ru.endroad.composable.TwoActionStub
import ru.endroad.econom.feature.wish.active.R

@Composable
fun RenderNoDesireStub(doTheMainAction: () -> Unit) = OneActionStub(
	painter = painterResource(id = R.drawable.empty),
	contentDescription = null,
	titleText = stringResource(id = R.string.noDesireTitle),
	descriptionText = stringResource(id = R.string.noDesireDescriptor),
	mainButtonText = stringResource(id = R.string.noDesireMainButton),
	doTheMainAction = doTheMainAction,
)

@Composable
fun RenderAllCompletedStub(doTheMainAction: () -> Unit, doTheSecondaryAction: () -> Unit) = TwoActionStub(
	painter = painterResource(id = R.drawable.all_completed),
	contentDescription = null,
	titleText = stringResource(id = R.string.wishesFulfilledTitle),
	descriptionText = stringResource(id = R.string.wishesFulfilledDescriptor),
	mainButtonText = stringResource(id = R.string.wishesFulfilledMainButton),
	secondaryButtonText = stringResource(id = R.string.wishesFulfilledSecondButton),
	doTheMainAction = doTheMainAction,
	doTheSecondaryAction = doTheSecondaryAction
)