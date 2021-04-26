package ru.endroad.feature.wish.detail.domain

sealed class ValidationResult {
	object Unchecked : ValidationResult()
	object Valid : ValidationResult()
	class Invalid(val reason: String) : ValidationResult()
}