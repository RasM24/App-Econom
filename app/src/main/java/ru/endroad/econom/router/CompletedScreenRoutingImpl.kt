package ru.endroad.econom.router

import androidx.fragment.app.FragmentManager
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.feature.stub.view.NoCompletedStubFragment
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedScreenRouting
import ru.endroad.navigation.routing.FragmentRouting
import ru.endroad.navigation.routing.back
import ru.endroad.navigation.routing.forwardTo

class CompletedScreenRoutingImpl(override val fragmentManager: FragmentManager) : CompletedScreenRouting, FragmentRouting {

	override fun showStubNoCompleted() {
		back()
		forwardTo(NoCompletedStubFragment(), R.id.content)
	}
}