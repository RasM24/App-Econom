package ru.endroad.feature.wish.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.wish_edit_fragment.apply_button
import kotlinx.android.synthetic.main.wish_edit_fragment.input_cost
import kotlinx.android.synthetic.main.wish_edit_fragment.input_cost_layout
import kotlinx.android.synthetic.main.wish_edit_fragment.input_important
import kotlinx.android.synthetic.main.wish_edit_fragment.input_important_layout
import kotlinx.android.synthetic.main.wish_edit_fragment.input_info
import kotlinx.android.synthetic.main.wish_edit_fragment.input_name
import kotlinx.android.synthetic.main.wish_edit_fragment.input_name_layout
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.endroad.feature.wish.detail.R
import ru.endroad.feature.wish.detail.presentation.EditScreenEvent
import ru.endroad.feature.wish.detail.presentation.EditScreenState
import ru.endroad.feature.wish.detail.presentation.EditWishViewModel
import ru.endroad.shared.wish.core.entity.Importance

class EditWishFragment : Fragment() {

	private val wishId: Int? by lazy { arguments?.getInt(WISH_ID) }

	private val presenter by viewModel<EditWishViewModel> { parametersOf(wishId) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.wish_edit_fragment, container, false)

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		presenter.state.asStateFlow()
			.onEach(::render)
			.launchIn(lifecycleScope)

		input_name.bindChangeFocus(EditScreenEvent::NameInputLostFocus, EditScreenEvent::NameInputReceiveFocus)
		input_info.bindChangeFocus(EditScreenEvent::InfoInputLostFocus, EditScreenEvent::InfoInputReceiveFocus)
		input_cost.bindChangeFocus(EditScreenEvent::CostInputLostFocus, EditScreenEvent::CostInputReceiveFocus)
		input_important.bindChangeFocus(EditScreenEvent::ImportanceInputLostFocus, EditScreenEvent::ImportanceInputReceiveFocus)

		apply_button.setOnClickListener {
			presenter.reduce(
				EditScreenEvent.ApplyClick(
					name = input_name.text.toString(),
					cost = input_cost.text.toString(),
					importance = input_important.text.toString(),
					info = input_info.text.toString()
				)
			)
		}

		//TODO вынести в presenter-слой
		val importances = Importance.values().map(Importance::name)
		val adapter = ArrayAdapter(requireContext(), R.layout.dropdown, importances)
		input_important.setAdapter(adapter)
	}

	private suspend fun render(state: EditScreenState) {
		when (state) {
			EditScreenState.InitialNewWish     -> renderNewWishScreen()
			is EditScreenState.InitialEditWish -> renderEditWishScreen(state)
			is EditScreenState.Validating      -> renderValidatingFieldsScreen(state)
			EditScreenState.WishSaved          -> requireFragmentManager().popBackStack()
		}
	}

	private fun renderEditWishScreen(state: EditScreenState.InitialEditWish) {
		requireActivity().title = getString(R.string.edit_screen_editing_wish)
		apply_button.setText(R.string.edit_screen_edit_wish)

		lifecycleScope.launch {
			state.wish.await().run {
				input_name.setText(name)
				input_cost.setText("$cost")
				input_info.setText(info)
				input_important.setText(importance.name, false)
			}
		}
	}

	private fun renderNewWishScreen() {
		requireActivity().title = getString(R.string.edit_screen_new_wish)
		apply_button.setText(R.string.edit_screen_add_wish)
	}

	private fun renderValidatingFieldsScreen(state: EditScreenState.Validating) {
		state.costField?.let { input_cost_layout.defineError(R.string.cost_input_error, !it) }
		state.importanceField?.let { input_important_layout.defineError(R.string.importance_input_error, !it) }
		state.nameField?.let {
			input_name_layout.error = when (it) {
				ru.endroad.feature.wish.detail.presentation.NameFieldValidate.EMPTY    -> resources.getString(R.string.name_input_error_empty)
				ru.endroad.feature.wish.detail.presentation.NameFieldValidate.LONG     -> resources.getString(R.string.name_input_error_long)
				ru.endroad.feature.wish.detail.presentation.NameFieldValidate.VALIDATE -> null
			}
		}
	}

	private fun TextInputLayout.defineError(@StringRes textErrorId: Int, showError: Boolean) {
		error = if (showError) resources.getString(textErrorId) else null
	}

	private fun EditText.bindChangeFocus(
		onLostFocusEvent: (String) -> EditScreenEvent,
		onReceiveFocusEvent: () -> EditScreenEvent
	) {
		setOnFocusChangeListener { _, isFocused ->
			val event = if (isFocused) onReceiveFocusEvent() else onLostFocusEvent(text.toString())
			presenter.reduce(event)
		}
	}

	companion object {
		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().apply {
				wishId?.let { arguments = bundleOf(WISH_ID to wishId) }
			}
	}
}