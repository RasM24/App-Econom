package ru.endroad.econom.feature.wishes.view

import android.widget.ArrayAdapter
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
import ru.endroad.arena.viewmodellayer.await
import ru.endroad.birusa.feature.wishes.R
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.feature.wishes.entity.EditScreenEvent
import ru.endroad.econom.feature.wishes.presenter.WishEditViewModel
import ru.endroad.navigation.finish

//TODO есть баг - при инвалидации поля он не возвращается к состоянию неошибки. Исправить с переходом на MVI
class EditWishFragment : BaseFragment(), CoroutineScope by CoroutineScope(uiDispatcher) {

	private val wishId: Int? by argumentOptional(WISH_ID)
	private val viewModel: IWishEditViewModel by viewModel<WishEditViewModel> { parametersOf(wishId) }

	override val layout: Int = R.layout.wish_edit_fragment

	override fun setupViewComponents() {

		val importances = Importance.values()
			.map(Importance::name)

		val adapter = ArrayAdapter(
			context!!,
			R.layout.dropdown,
			importances
		)

		input_important.setAdapter(adapter)
	}

	override fun setupViewModel() {
		when (val state = viewModel.state) {
			NewWishState     -> renderAddWish()
			is EditWishState -> renderEditWish(state)
		}
		viewModel.validation.subcribe(this) { validation ->
			if (validation.validate)
				finish()

			if (!validation.nameField) input_name_layout.error = "Текст не должен быть длиннее 40 символов"
			if (!validation.costField) input_cost_layout.error = "Введите ценник"
			if (!validation.importanceField) input_important_layout.error = "Выберите важность"
		}
	}

	private fun renderEditWish(wishState: EditWishState) {
		title = "Изменить"
		apply.text = "Изменить"

		await(wishState.wish) {
			input_name.setText(name)
			input_cost.setText("$cost")
			apply.setOnClickListener {
				viewModel.event(
					EditScreenEvent.Apply(
						name = input_name.text.toString(),
						cost = input_cost.text.toString(),
						importance = input_important.text.toString(),
						info = input_info.text.toString()
					)
				)
			}
		}
	}

	private fun renderAddWish() {
		title = "Добавить"
		apply.text = "Добавить"
		apply.setOnClickListener {
			viewModel.event(
				EditScreenEvent.Apply(
					name = input_name.text.toString(),
					cost = input_cost.text.toString(),
					importance = input_important.text.toString(),
					info = input_info.text.toString()
				)
			)
		}
	}

	companion object {
		const val WISH_ID = "WISH_ID"

		fun getInstance(wishId: Int? = null): Fragment =
			EditWishFragment().withArgument {
				wishId?.let { putInt(WISH_ID, it) }
			}
	}
}