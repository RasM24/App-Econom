package ru.endroad.econom.feature.wishes.view

import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.wish_edit_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.endroad.arena.data.uiDispatcher
import ru.endroad.arena.mvi.view.MviView
import ru.endroad.arena.viewlayer.extension.argumentOptional
import ru.endroad.arena.viewlayer.extension.withArgument
import ru.endroad.arena.viewlayer.fragment.BaseFragment
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.entity.EditScreenState
import ru.endroad.econom.feature.wishes.presenter.EditWishViewModel
import ru.endroad.navigation.finish

class EditWishFragment : BaseFragment(),
						 MviView<EditScreenState, EditScreenEvent>,
						 CoroutineScope by CoroutineScope(uiDispatcher) {

	override val layout: Int = R.layout.wish_edit_fragment

	private val wishId: Int? by argumentOptional(WISH_ID)

	override val presenter by viewModel<EditWishViewModel> { parametersOf(wishId) }

	override val render = { state: EditScreenState ->
		when (state) {
			EditScreenState.InitialNewWish     -> renderNewWishScreen()
			is EditScreenState.InitialEditWish -> renderEditWishScreen(state)
			is EditScreenState.Validating      -> renderValidatingFieldsScreen(state)
			EditScreenState.WishSaved          -> finish()
		}
	}

	override fun setupViewComponents() {
		bindRenderState(this)

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

		//TODO вынести в presenter-слой
		val importances = Importance.values().map(Importance::name)
		val adapter = ArrayAdapter(requireContext(), R.layout.dropdown, importances)
		input_important.setAdapter(adapter)
	}

	private fun renderEditWishScreen(state: EditScreenState.InitialEditWish) {
		title = getString(R.string.edit_screen_editing_wish)
		apply.setText(R.string.edit_screen_edit_wish)

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

	private fun renderNewWishScreen() {
		title = getString(R.string.edit_screen_new_wish)
		apply.setText(R.string.edit_screen_add_wish)
	}

	private fun renderValidatingFieldsScreen(state: EditScreenState.Validating) {
		state.nameField?.let { input_name_layout.defineError(R.string.name_input_error, !it) }
		state.costField?.let { if (it) input_cost_layout.defineError(R.string.cost_input_error, !it) }
		state.importanceField?.let { input_important_layout.defineError(R.string.importance_input_error, !it) }
	}

	private fun TextInputLayout.defineError(@StringRes textErrorId: Int, showError: Boolean) {
		error = if (showError) resources.getString(textErrorId) else null
	}

	companion object {
		//TODO перейти на другой вариант использования аргументов
		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().withArgument {
				wishId?.let { putInt(WISH_ID, it) }
			}
	}
}