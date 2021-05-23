package ru.endroad.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect

@Composable
fun AutofillTextField(
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
fun MaterialTextField(
	modifier: Modifier = Modifier,
	value: TextFieldValue,
	onValueChange: (TextFieldValue) -> Unit,
	dischargeErrorState: (() -> Unit)? = null,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = LocalTextStyle.current,
	label: @Composable (() -> Unit)? = null,
	helper: @Composable (BoxScope.() -> Unit)? = null,
	counter: @Composable (BoxScope.() -> Unit)? = null,
	maxCountSymbols: Int? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	isError: Boolean = false,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions(),
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
	LaunchedEffect(interactionSource) {
		interactionSource.interactions.collect { interaction ->
			if (interaction is FocusInteraction.Focus) dischargeErrorState?.invoke()
		}
	}

	val trailing = trailingIcon ?: { if (isError) Icon(imageVector = Icons.Filled.ErrorOutline, contentDescription = "Error") }

	Column(modifier = modifier.padding(vertical = 4.dp)) {
		OutlinedTextField(
			value = value,
			onValueChange = {
				when {
					maxCountSymbols == null           -> onValueChange(it)
					it.text.length <= maxCountSymbols -> onValueChange(it)
					else                              -> Unit
				}
			},
			modifier = Modifier.fillMaxWidth(),
			enabled = enabled,
			readOnly = readOnly,
			textStyle = textStyle,
			label = label,
			leadingIcon = leadingIcon,
			trailingIcon = trailing,
			isError = isError,
			visualTransformation = visualTransformation,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = singleLine,
			maxLines = maxLines,
			interactionSource = interactionSource,
			colors = colors,
		)

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 2.dp, horizontal = 2.dp)
		) {
			Decoration(
				contentColor = colors.indicatorColor(enabled = enabled, isError = isError, interactionSource = interactionSource).value,
				typography = MaterialTheme.typography.caption,
				content = { Box(content = helper ?: { }) }
			)
			Spacer(modifier = Modifier.weight(1f))
			Decoration(
				contentColor = colors.indicatorColor(enabled = enabled, isError = isError, interactionSource = interactionSource).value,
				typography = MaterialTheme.typography.caption,
				content = { Box(content = counter ?: { }) }
			)
		}
	}
}

@Composable
private fun Decoration(
	contentColor: Color,
	typography: TextStyle,
	content: @Composable () -> Unit
) {
	CompositionLocalProvider(
		LocalContentColor provides contentColor,
		LocalContentAlpha provides ContentAlpha.medium,
		LocalTextStyle provides typography,
		content = content
	)
}