package ru.endroad.econom.feature.wishes.view

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.wish_edit_fragment.*
import kotlinx.coroutines.CoroutineScope
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
import ru.endroad.econom.feature.wishes.presenter.WishEditViewModel
import ru.endroad.navigation.finish

//TODO есть баг - при инвалидации поля он не возвращается к состоянию неошибки. Исправить с переходом на MVI
class EditWishFragment : BaseFragment(), CoroutineScope by CoroutineScope(uiDispatcher) {

	private val wishId: Int? by argumentOptional(WISH_ID)
	private val viewModel: IWishEditViewModel by viewModel<WishEditViewModel> { parametersOf(wishId) }

	override val layout: Int = R.layout.wish_edit_fragment

	override fun setupViewComponents() {
		input_name.bindChangeFocus(EditScreenEvent::NameInputChangeFocus)
		input_info.bindChangeFocus(EditScreenEvent::InfoInputChangeFocus)
		input_cost.bindChangeFocus(EditScreenEvent::CostInputChangeFocus)
		input_important.bindChangeFocus(EditScreenEvent::ImportanceInputChangeFocus)

		apply.bindClick {
			EditScreenEvent.ApplyClick(name = input_name.text.toString(),
									   cost = input_cost.text.toString(),
									   importance = input_important.text.toString(),
									   info = input_info.text.toString())
		}

		val importances = Importance.values().map(Importance::name)

		val adapter = ArrayAdapter(
			context!!, R.layout.dropdown, importances
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

		state.wish.run {
			input_name.setText(name)
			input_cost.setText("$cost")
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

	private fun EditText.bindChangeFocus(onLostFocus: (String, Boolean) -> EditScreenEvent) {
		setOnFocusChangeListener { _, hasFocus ->
			viewModel.event(onLostFocus(text.toString(), hasFocus))
		}
	}

	private fun Button.bindClick(click: () -> EditScreenEvent) {
		setOnClickListener { viewModel.event(click()) }
	}

	companion object {
		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().withArgument {
				wishId?.let { putInt(WISH_ID, it) }
			}
	}
}