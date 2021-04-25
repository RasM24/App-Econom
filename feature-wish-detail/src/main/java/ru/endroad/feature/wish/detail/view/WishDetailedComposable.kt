package ru.endroad.feature.wish.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.endroad.composable.MaterialTextField
import ru.endroad.composable.PrimaryText
import ru.endroad.composable.SecondaryText
import ru.endroad.feature.wish.detail.domain.CostValidator
import ru.endroad.feature.wish.detail.domain.ImportanceValidator
import ru.endroad.feature.wish.detail.domain.NameValidator
import ru.endroad.feature.wish.detail.domain.ValidationResult
import ru.endroad.shared.wish.core.entity.Importance

@Composable
fun RenderWishDetail(
	nameDraft : String? = null,
	infoDraft : String? = null,
	costDraft : String? = null,
	importanceDraft : String? = null,
	createWish: (name: String, info: String, cost: String, importance: String) -> Unit,
) {
	Column(modifier = Modifier.fillMaxSize()) {
		val maxCountSymbols = 40

		var nameFieldState by remember { mutableStateOf(TextFieldValue(text = nameDraft ?: "")) }
		var infoFieldState by remember { mutableStateOf(TextFieldValue(text = infoDraft ?: "")) }
		var costFieldState by remember { mutableStateOf(TextFieldValue(text = costDraft ?: "")) }
		var importanceFieldState by remember { mutableStateOf(TextFieldValue(text = importanceDraft ?: "")) }

		var nameFieldErrorState: ValidationResult by remember { mutableStateOf(ValidationResult.Unchecked) }
		var costFieldErrorState: ValidationResult by remember { mutableStateOf(ValidationResult.Unchecked) }
		var importanceFieldErrorState: ValidationResult by remember { mutableStateOf(ValidationResult.Unchecked) }

		ScrolledColumn(
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 16.dp)
		) {
			Spacer(modifier = Modifier.height(24.dp))

			PrimaryText(text = "Основная информация", color = MaterialTheme.colors.primary)

			MaterialTextField(
				modifier = Modifier.fillMaxWidth(),
				value = nameFieldState,
				onValueChange = { nameFieldState = it },
				dischargeErrorState = { nameFieldErrorState = ValidationResult.Unchecked },
				singleLine = true,
				label = { SecondaryText(text = "Введите мечту*") },
				maxCountSymbols = maxCountSymbols,
				helper = (nameFieldErrorState as? ValidationResult.Invalid)?.let { errorText(it) },
				counter = { Text(text = "${nameFieldState.text.length}/$maxCountSymbols") },
				isError = nameFieldErrorState is ValidationResult.Invalid,
			)

			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				value = infoFieldState,
				onValueChange = { infoFieldState = it },
				label = { SecondaryText(text = "Опишите мечту") },
			)

			Spacer(modifier = Modifier.height(16.dp))

			PrimaryText(text = "Детали", color = MaterialTheme.colors.primary)

			MaterialTextField(
				modifier = Modifier.fillMaxWidth(),
				value = costFieldState,
				onValueChange = { costFieldState = it },
				dischargeErrorState = { costFieldErrorState = ValidationResult.Unchecked },
				label = { SecondaryText(text = "Цена*") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				helper = (costFieldErrorState as? ValidationResult.Invalid)?.let { errorText(it) },
				isError = costFieldErrorState is ValidationResult.Invalid,
			)

			AutofillTextField(
				modifier = Modifier.fillMaxWidth(),
				value = importanceFieldState,
				onValueChange = { importanceFieldState = it },
				dischargeErrorState = { importanceFieldErrorState = ValidationResult.Unchecked },
				label = { SecondaryText(text = "Важность*") },
				suggestions = Importance.values().map(Importance::name),
				helper = (importanceFieldErrorState as? ValidationResult.Invalid)?.let { errorText(it) },
				isError = importanceFieldErrorState is ValidationResult.Invalid,
			)
		}


		PinnedButton(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			text = "Создать",
			onClick = {
				nameFieldErrorState = NameValidator(40).invoke(nameFieldState.text)
				costFieldErrorState = CostValidator(costFieldState.text)
				importanceFieldErrorState = ImportanceValidator(importanceFieldState.text)

				//TODO кривая логика
				if (nameFieldErrorState is ValidationResult.Valid &&
					costFieldErrorState is ValidationResult.Valid &&
					importanceFieldErrorState is ValidationResult.Valid
				) {
					createWish(nameFieldState.text, infoFieldState.text, costFieldState.text, importanceFieldState.text)
				}
			})
	}
}