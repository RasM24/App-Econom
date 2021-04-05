package ru.endroad.econom.router

import ru.endroad.component.core.Navigator
import ru.endroad.component.core.back
import ru.endroad.econom.feature.stub.view.NoCompletedStubFragment
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedScreenRouting

class CompletedScreenRoutingImpl(private val navigator: Navigator) : CompletedScreenRouting {

	override fun showStubNoCompleted() {
		navigator.hubActivity?.supportFragmentManager?.back()
		navigator.open(NoCompletedStubFragment())
	}
}