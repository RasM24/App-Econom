package ru.endroad.econom.feature.wishes.domain

class NameValidator(private val maxLengthName: Int) {

	fun isNotEmpty(value: String): Boolean = value.isNotEmpty()

	fun isNotLong(value: String): Boolean = value.length <= maxLengthName
}