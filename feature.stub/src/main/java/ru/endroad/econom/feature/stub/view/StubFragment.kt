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

abstract class StubFragment : Fragment() {

	abstract val imageId: Int
	abstract val titleText: Int
	open val descriptionText: Int? = null
	open val mainButtonText: Int? = null
	open val secondaryButtonText: Int? = null

	open val doTheMainAction: () -> Unit = {}
	open val doTheSecondaryAction: () -> Unit = {}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			TwoActionStub(
				painter = painterResource(id = imageId),
				contentDescription = null,
				titleText = stringResource(id = titleText),
				descriptionText = descriptionText?.let { stringResource(id = it) },
				mainButtonText = mainButtonText?.let { stringResource(id = it) },
				secondaryButtonText = secondaryButtonText?.let { stringResource(id = it) },
				doTheMainAction = doTheMainAction,
				doTheSecondaryAction = doTheSecondaryAction
			)
		}
}