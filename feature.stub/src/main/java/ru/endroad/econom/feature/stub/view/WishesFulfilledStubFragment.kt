package ru.endroad.econom.feature.stub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.endroad.component.core.setComposeView

@Deprecated("Переход на Jetpack Compose")
class WishesFulfilledStubFragment(
	private val doTheMainAction: () -> Unit,
	private val doTheSecondaryAction: () -> Unit
) : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			RenderAllCompletedStub(doTheMainAction, doTheSecondaryAction)
		}
}