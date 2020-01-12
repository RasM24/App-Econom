package ru.endroad.econom.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.feature.stub.view.NoDesireStubFragment
import ru.endroad.econom.feature.stub.view.WishesFulfilledStubFragment
import ru.endroad.econom.feature.wishes.WishFlowRouting
import ru.endroad.econom.feature.wishes.view.EditWishFragment
import ru.endroad.navigation.routing.FragmentRouting
import ru.endroad.navigation.routing.changeRoot
import ru.endroad.navigation.routing.forwardTo

class WishFlowRoutingImpl(override val fragmentManager: FragmentManager) : WishFlowRouting, FragmentRouting {

	//TODO нельзя так, надо что-нибудь придумать
	var fragment: Fragment? = null

	override fun openWishEditScreen(wishId: Int) = forwardTo(EditWishFragment.getInstance(wishId), R.id.content)

	override fun openWishNewScreen() = forwardTo(EditWishFragment.getInstance(), R.id.content)

	override fun showStubNoDesire() {
		fragment = fragmentManager.findFragmentById(R.id.content)

		fragment?.let { fragmentManager.beginTransaction().detach(it).commit() }
		changeRoot(NoDesireStubFragment(::openWishNewScreen), R.id.content)
	}

	override fun showStubWishesFulfilled() {
		fragment = fragmentManager.findFragmentById(R.id.content)

		fragment?.let { fragmentManager.beginTransaction().detach(it).commit() }
		changeRoot(WishesFulfilledStubFragment(::openWishNewScreen), R.id.content)
	}

	override fun closeStub() {
		fragment?.let {
			fragmentManager.beginTransaction().attach(it).commit()
			changeRoot(it, R.id.content)
		}
	}
}