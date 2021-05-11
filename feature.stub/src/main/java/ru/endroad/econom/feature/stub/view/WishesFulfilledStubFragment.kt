package ru.endroad.econom.feature.stub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import ru.endroad.component.core.setComposeView
import ru.endroad.composable.TwoActionStub
import ru.endroad.econom.feature.stub.R

class WishesFulfilledStubFragment(
	private val doTheMainAction: () -> Unit,
	private val doTheSecondaryAction: () -> Unit
) : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			TwoActionStub(
				painter = painterResource(id = R.drawable.all_completed),
				contentDescription = null,
				titleText = stringResource(id = R.string.wishesFulfilledTitle),
				descriptionText = stringResource(id = R.string.wishesFulfilledDescriptor),
				mainButtonText = stringResource(id = R.string.wishesFulfilledMainButton),
				secondaryButtonText = stringResource(id = R.string.wishesFulfilledSecondButton),
				doTheMainAction = doTheMainAction,
				doTheSecondaryAction = doTheSecondaryAction
			)
		}
}