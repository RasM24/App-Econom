package ru.endroad.component.room

import androidx.sqlite.db.SupportSQLiteDatabase

fun SupportSQLiteDatabase.runTransaction(transaction: SupportSQLiteDatabase.() -> Unit) {
	try {
		beginTransaction()
		this.transaction()
		setTransactionSuccessful()
	} finally {
		endTransaction()
	}
}