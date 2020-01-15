package ru.endroad.econom.feature.stub.view

import ru.endroad.econom.feature.stub.R

class NoCompletedStubFragment : StubFragment() {

	override val imageId = R.drawable.empty
	override val titleText = R.string.noCompletedTitle
	override val descriptionText = R.string.noCompletedDescriptor
}