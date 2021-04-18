package ru.endroad.econom.mock

import androidx.sqlite.db.SupportSQLiteDatabase
import ru.endroad.component.core.runTransaction
import ru.endroad.econom.component.wish.datasource.WISH_TABLE
import ru.endroad.econom.component.wish.model.WishModel

const val SQL_INSERT_WISH = "INSERT INTO $WISH_TABLE (name, cost, importance, info, complete) VALUES(?,?,?,?,?)"
const val SQL_UPDATE_WISH = "UPDATE $WISH_TABLE SET name = ?,cost = ?,importance = ?,info= ?,complete = ? WHERE id = ?"

val WishModel.arrayValues get() = arrayOf(name, cost, importance, info, complete)

fun SupportSQLiteDatabase.mockWishes() = runTransaction {
	wishes.forEach { wish -> execSQL(SQL_INSERT_WISH, wish.arrayValues) }
}

val wishes = mutableListOf(
	WishModel(name = "Автомобиль Lexus", cost = 400000, importance = "LITTLE", info = "Lexus GS300, на коже с черным салоном"),
	WishModel(name = "Велосипед", cost = 30000, importance = "LITTLE", info = "На лето хорошо бы купить велосипед"),
	WishModel(name = "Аккумулятор", importance = "CRITICAL", cost = 4000),
	WishModel(name = "Передние развальные рычаги", importance = "BACKLOG", cost = 8000, info = "Hardrace, на заказ"),
	WishModel(name = "Задние развальные рычаги", importance = "BACKLOG", cost = 8000, info = "Hardrace, на заказ"),
	WishModel(name = "X-bar", cost = 14000, importance = "BACKLOG", info = "На заказ из челябинска"),
	WishModel(name = "Проставки на колеса", cost = 5000, importance = "BACKLOG", info = "5-10мм"),
	WishModel(name = "Вакуумник 1 дюйм", importance = "LITTLE", cost = 1500),
	WishModel(name = "Распределение тормозного усилия", cost = 1500, importance = "BACKLOG"),
	WishModel(name = "Ремкоплект на передние тормоза", cost = 3000, importance = "CRITICAL", info = "Партномер: 01463-S04-000"),
	WishModel(name = "Задние дисковые тормоза", cost = 10000, importance = "BIG", info = "Можно купить у Сани"),
	WishModel(name = "Cut Fender", cost = 4000, importance = "BACKLOG", info = "На заказ от RT-Honda"),
	WishModel(name = "Комплект передних фар", cost = 6000, importance = "BIG", info = "На заказ из Питера"),
	WishModel(name = "Колесные диски", importance = "LITTLE", cost = 50000),
	WishModel(name = "Сцепление/Выжимной подшипник", importance = "LITTLE", cost = 3000),
	WishModel(name = "Лобовое стекло", importance = "LITTLE", cost = 7000)
)

val wishesLastPosition = wishes.size - 1
