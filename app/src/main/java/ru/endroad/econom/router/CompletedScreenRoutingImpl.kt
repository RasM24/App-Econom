package ru.endroad.econom.router

import ru.endroad.component.core.Navigator
import ru.endroad.component.core.back
import ru.endroad.econom.feature.wishes.completed.presenter.CompletedScreenRouting
import ru.endroad.econom.feature.wishes.completed.view.NoCompletedStubFragment

class CompletedScreenRoutingImpl(private val navigator: Navigator) : CompletedScreenRouting {

	override fun showStubNoCompleted() {
		navigator.hubActivity?.supportFragmentManager?.back()
		navigator.open(NoCompletedStubFragment())
	}
}