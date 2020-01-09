package ru.endroad.econom.feature.stub.view

import ru.endroad.econom.feature.stub.R

class WishesFulfilledStubFragment(override val doTheMainAction: () -> Unit) : StubFragment() {

	override val imageId = R.drawable.all_completed
	override val titleText = R.string.wishesFulfilledTitle
	override val descriptionText = R.string.wishesFulfilledDescriptor
	override val mainButtonText = R.string.wishesFulfilledMainButton
}