package ru.endroad.econom.feature.wishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.endroad.component.core.setComposeView

@Deprecated("Переход на Jetpack Compose")
class NoDesireStubFragment(private val doTheMainAction: () -> Unit) : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		setComposeView {
			RenderNoDesireStub(doTheMainAction)
		}
}