package ru.endroad.econom.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.component.core.Navigator
import ru.endroad.component.core.changeRoot
import ru.endroad.econom.feature.stub.view.NoDesireStubFragment
import ru.endroad.econom.feature.stub.view.WishesFulfilledStubFragment
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.completed.view.CompletedWishesFragment
import ru.endroad.econom.feature.wishes.view.EditWishFragment

class WishFlowRoutingImpl(private val navigator: Navigator) : WishFlowRouting {

	//TODO нельзя так, надо что-нибудь придумать
	private var fragment: Fragment? = null

	private val fragmentManager : FragmentManager get() = navigator.hubActivity.let(::requireNotNull).supportFragmentManager

	override fun openWishEditScreen(wishId: Int) = navigator.open(EditWishFragment.getInstance(wishId))

	override fun openWishNewScreen() = navigator.open(EditWishFragment.getInstance())

	override fun showStubNoDesire() {
		fragment = fragmentManager.findFragmentById(R.id.content)

		fragment?.let { fragmentManager.beginTransaction().detach(it).commit() }
		fragmentManager.changeRoot(NoDesireStubFragment(::openWishNewScreen), R.id.content)
	}

	override fun showStubWishesFulfilled() {
		fragment = fragmentManager.findFragmentById(R.id.content)

		fragment?.let { fragmentManager.beginTransaction().detach(it).commit() }
		fragmentManager.changeRoot(WishesFulfilledStubFragment(::openWishNewScreen, ::openCompletedWishScreen), R.id.content)
	}

	override fun closeStub() {
		fragment?.let {
			fragmentManager.beginTransaction().attach(it).commit()
			fragmentManager.changeRoot(it, R.id.content)
		}
	}

	override fun openCompletedWishScreen() {
		navigator.open(CompletedWishesFragment())
	}
}