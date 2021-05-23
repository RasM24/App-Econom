package ru.endroad.feature.wish.detail.domain

class NameValidator(private val maxLengthName: Int) {

	operator fun invoke(value: String): ValidationResult =
		when {
			value.isEmpty() -> ValidationResult.Invalid("Введите название") //TODO Hardcore Text
			value.isLong()  -> ValidationResult.Invalid("Текст не должен быть длиннее 40 символов") //TODO Hardcore Text
			else            -> ValidationResult.Valid
		}

	private fun String.isLong(): Boolean = length > maxLengthName
}