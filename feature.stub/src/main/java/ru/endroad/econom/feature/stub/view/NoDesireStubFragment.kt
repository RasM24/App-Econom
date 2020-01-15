package ru.endroad.econom.feature.stub.view

import ru.endroad.econom.feature.stub.R

class NoDesireStubFragment(override val doTheMainAction: () -> Unit) : StubFragment() {

	override val imageId = R.drawable.empty
	override val titleText = R.string.noDesireTitle
	override val descriptionText = R.string.noDesireDescriptor
	override val mainButtonText = R.string.noDesireMainButton
}