package ru.endroad.econom.router

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

	override fun openWishEditScreen(wishId: Int) = forwardTo(EditWishFragment.getInstance(wishId), R.id.content)

	override fun openWishNewScreen() = forwardTo(EditWishFragment.getInstance(), R.id.content)

	override fun showStubNoDesire() = changeRoot(NoDesireStubFragment(::openWishNewScreen), R.id.content)

	override fun showStubWishesFulfilled() = changeRoot(WishesFulfilledStubFragment(::openWishNewScreen), R.id.content)
}