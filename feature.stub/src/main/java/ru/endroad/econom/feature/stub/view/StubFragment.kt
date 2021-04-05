package ru.endroad.econom.feature.stub.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.stub_fragment.*
import ru.endroad.econom.feature.stub.R

abstract class StubFragment : Fragment() {

	private val layout = R.layout.stub_fragment

	abstract val imageId: Int
	abstract val titleText: Int
	open val descriptionText: Int? = null
	open val mainButtonText: Int? = null
	open val secondaryButtonText: Int? = null

	open val doTheMainAction: () -> Unit = {}
	open val doTheSecondaryAction: () -> Unit = {}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(layout, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		setupViewComponents()
	}

	private fun setupViewComponents() {
		image.setImageResource(imageId)

		title.setText(titleText)
		description.setOrHide(descriptionText)
		mainButton.setOrHide(mainButtonText)
		secondaryButton.setOrHide(secondaryButtonText)

		mainButton.setOnClickListener { doTheMainAction() }
		secondaryButton.setOnClickListener { doTheSecondaryAction() }
	}

	private fun TextView.setOrHide(stringId: Int?) {
		if (stringId != null) {
			setText(stringId)
			visibility = View.VISIBLE
		} else {
			visibility = View.GONE
		}
	}
}