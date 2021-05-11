package ru.endroad.econom.feature.stub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import ru.endroad.component.core.setComposeView
import ru.endroad.composable.ImageStub
import ru.endroad.econom.feature.stub.R

class NoCompletedStubFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			ImageStub(
				painter = painterResource(id = R.drawable.empty),
				contentDescription = null,
				titleText = stringResource(id = R.string.noCompletedTitle),
				descriptionText = stringResource(id = R.string.noCompletedDescriptor),
			)
		}
}