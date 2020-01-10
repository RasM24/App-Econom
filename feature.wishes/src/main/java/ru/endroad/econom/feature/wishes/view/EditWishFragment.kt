package ru.endroad.econom.feature.wishes.view

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.wish_edit_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.endroad.arena.data.flow.extension.subcribe
import ru.endroad.arena.data.uiDispatcher
import ru.endroad.arena.viewlayer.extension.argumentOptional
import ru.endroad.arena.viewlayer.extension.withArgument
import ru.endroad.arena.viewlayer.fragment.BaseFragment
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenState
import ru.endroad.econom.feature.wishes.presenter.EditWishViewModel
import ru.endroad.navigation.finish

class EditWishFragment : BaseFragment(), CoroutineScope by CoroutineScope(uiDispatcher) {

	private val wishId: Int? by argumentOptional(WISH_ID)
	private val viewModel by viewModel<EditWishViewModel> { parametersOf(wishId) }

	override val layout: Int = R.layout.wish_edit_fragment

	override fun setupViewComponents() {
		input_name.bindChangeFocus(EditScreenEvent::NameInputLostFocus, EditScreenEvent::NameInputReceiveFocus)
		input_info.bindChangeFocus(EditScreenEvent::InfoInputLostFocus, EditScreenEvent::InfoInputReceiveFocus)
		input_cost.bindChangeFocus(EditScreenEvent::CostInputLostFocus, EditScreenEvent::CostInputReceiveFocus)
		input_important.bindChangeFocus(EditScreenEvent::ImportanceInputLostFocus, EditScreenEvent::ImportanceInputReceiveFocus)

		apply.bindClick {
			EditScreenEvent.ApplyClick(name = input_name.text.toString(),
									   cost = input_cost.text.toString(),
									   importance = input_important.text.toString(),
									   info = input_info.text.toString())
		}

		val importances = Importance.values()
			.map(Importance::name)

		val adapter = ArrayAdapter(
			requireContext(), R.layout.dropdown, importances
		)

		input_important.setAdapter(adapter)
	}

	override fun setupViewModel() {
		viewModel.state.subcribe(this) { state ->
			when (state) {
				EditScreenState.InitialNewWish     -> renderAddWish()
				is EditScreenState.InitialEditWish -> renderEditWish(state)
				is EditScreenState.Validating      -> renderValidatingFields(state)
				EditScreenState.WishSaved          -> finish()
			}
		}
	}

	private fun renderEditWish(state: EditScreenState.InitialEditWish) {
		title = "Изменить"
		apply.text = "Изменить"

		//TODO придумать, как избавиться от корутин на view-слое
		CoroutineScope(uiDispatcher).launch {
			state.wish.await().run {
				input_name.setText(name)
				input_cost.setText("$cost")
				input_info.setText(info)
				input_important.setText(importance.name, false)
			}
		}
	}

	private fun renderAddWish() {
		title = "Добавить"
		apply.text = "Добавить"
	}

	private fun renderValidatingFields(state: EditScreenState.Validating) {
		state.nameField?.let {
			if (it) input_name_layout.error = null
			else input_name_layout.error = "Текст не должен быть длиннее 40 символов"
		}
		state.costField?.let {
			if (it) input_cost_layout.error = null
			else input_cost_layout.error = "Введите ценник"
		}
		state.importanceField?.let {
			if (it) input_important_layout.error = null
			else input_important_layout.error = "Выберите важность"
		}
	}

	private fun EditText.bindChangeFocus(onLostFocus: (String) -> EditScreenEvent, onReceiveFocus: () -> EditScreenEvent) {
		setOnFocusChangeListener { _, hasFocus ->
			if (hasFocus)
				viewModel.reduce(onReceiveFocus())
			else
				viewModel.reduce(onLostFocus(text.toString()))
		}
	}

	private fun Button.bindClick(onClick: () -> EditScreenEvent) {
		setOnClickListener { viewModel.reduce(onClick()) }
	}

	companion object {
		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().withArgument {
				wishId?.let { putInt(WISH_ID, it) }
			}
	}
}