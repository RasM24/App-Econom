package ru.endroad.econom.mock

import androidx.sqlite.db.SupportSQLiteDatabase
import ru.endroad.component.room.runTransaction
import ru.endroad.econom.component.wish.datasource.WISH_TABLE
import ru.endroad.econom.component.wish.model.WishModel

private const val SQL_INSERT_WISH = "INSERT INTO $WISH_TABLE (name, cost, importance, info, complete) VALUES(?,?,?,?,?)"

private val WishModel.arrayValues get() = arrayOf(name, cost, importance, info, complete)

fun SupportSQLiteDatabase.fillWishTable() = runTransaction {
	mockWishes.forEach { wish -> execSQL(SQL_INSERT_WISH, wish.arrayValues) }
}