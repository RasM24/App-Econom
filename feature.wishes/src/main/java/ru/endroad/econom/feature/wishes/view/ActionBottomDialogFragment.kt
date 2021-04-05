package ru.endroad.econom.feature.wishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import ru.endroad.birusa.feature.wishes.R

class ActionBottomDialogFragment(
	private val wishName: String,
	private val onClickCompleteListener: () -> Unit,
	private val onClickEditListener: () -> Unit,
	private val onClickDeleteListener: () -> Unit
) : BottomSheetDialogFragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.bottom_sheet, container, false)

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