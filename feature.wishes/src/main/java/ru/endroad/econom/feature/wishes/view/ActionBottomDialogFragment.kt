package ru.endroad.econom.feature.wishes.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import ru.endroad.arena.viewlayer.fragment.BaseBottomSheetDialogFragment
import ru.endroad.birusa.feature.wishes.R

class ActionBottomDialogFragment(
	private val wishName: String,
	private val onClickCompleteListener: () -> Unit,
	private val onClickEditListener: () -> Unit,
	private val onClickDeleteListener: () -> Unit
) : BaseBottomSheetDialogFragment() {

	override val layout: Int = R.layout.bottom_sheet

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		wish_name.text = wishName

		complete.setOnClickListener {
			onClickCompleteListener()
			dismiss()
		}
		edit.setOnClickListener {
			onClickEditListener()
			dismiss()
		}
		delete.setOnClickListener {
			onClickDeleteListener()
			dismiss()
		}
	}
}

//TODO попробовать вынести в роутер
//TODO перенести wishName в bundle
fun Fragment.showBottomSheetActionWish(
	wishName: String,
	onClickCompleteListener: () -> Unit,
	onClickEditListener: () -> Unit,
	onClickDeleteListener: () -> Unit
) = fragmentManager?.let {
	ActionBottomDialogFragment(
		wishName,
		onClickCompleteListener,
		onClickEditListener,
		onClickDeleteListener
	)
		.show(it, null)
}