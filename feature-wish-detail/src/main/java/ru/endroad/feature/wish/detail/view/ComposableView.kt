package ru.endroad.feature.wish.detail.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.endroad.composable.ActionText
import ru.endroad.composable.MaterialTextField
import ru.endroad.composable.PrimaryText
import ru.endroad.feature.wish.detail.domain.ValidationResult

@Composable
internal fun AutofillTextField(
	modifier: Modifier = Modifier,
	value: TextFieldValue,
	onValueChange: (TextFieldValue) -> Unit,
	dischargeErrorState: () -> Unit,
	enabled: Boolean = true,
	textStyle: TextStyle = LocalTextStyle.current,
	label: @Composable (() -> Unit)? = null,
	helper: @Composable (BoxScope.() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
	suggestions: List<String>,
	isError: Boolean = false,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions(),
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
	Box {
		val expanded = interactionSource.collectIsFocusedAsState()
		val focusManager = LocalFocusManager.current

		MaterialTextField(
			modifier = modifier,
			value = value,
			onValueChange = { onValueChange(it) },
			dischargeErrorState = dischargeErrorState,
			enabled = enabled,
			readOnly = true,
			textStyle = textStyle,
			label = label,
			helper = helper,
			leadingIcon = leadingIcon,
			trailingIcon =  { Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "choose") },
			isError = isError,
			visualTransformation = visualTransformation,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = singleLine,
			maxLines = maxLines,
			interactionSource = interactionSource,
			colors = colors,
		)

		DropdownMenu(
			expanded = expanded.value,
			onDismissRequest = { focusManager.clearFocus() }
		) {
			suggestions.forEach { name ->
				Box(modifier = Modifier
					.fillMaxWidth()
					.clickable {
						onValueChange(TextFieldValue(text = name))
						focusManager.clearFocus()
					}) {
					PrimaryText(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), text = name)
				}
			}
		}
	}
}

@Composable
internal fun ScrolledColumn(
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit
) {
	val scrollState = rememberScrollState()

	Column(
		modifier = modifier
			.fillMaxWidth()
			.verticalScroll(scrollState)
			.clipToBounds(),
		content = content,
	)
}

@Composable
internal fun PinnedButton(
	modifier: Modifier = Modifier,
	text: String,
	onClick: () -> Unit,
) = Box(modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)) {
	Button(modifier = modifier, onClick = onClick) {
		ActionText(text = text)
	}
}

@Composable
internal fun errorText(validationResult: ValidationResult.Invalid): @Composable (BoxScope.() -> Unit) =
	{ Text(text = validationResult.reason) }