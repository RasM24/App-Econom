package ru.endroad.econom.feature.stub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import ru.endroad.component.core.setComposeView
import ru.endroad.composable.OneActionStub
import ru.endroad.econom.feature.stub.R

class NoDesireStubFragment(private val doTheMainAction: () -> Unit) : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			OneActionStub(
				painter = painterResource(id = R.drawable.empty),
				contentDescription = null,
				titleText = stringResource(id = R.string.noDesireTitle),
				descriptionText = stringResource(id = R.string.noDesireDescriptor),
				mainButtonText = stringResource(id = R.string.noDesireMainButton),
				doTheMainAction = doTheMainAction,
			)
		}
}