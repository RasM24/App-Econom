package ru.endroad.econom.mock

import androidx.sqlite.db.SupportSQLiteDatabase
import ru.endroad.arena.data.runTransaction
import ru.endroad.econom.component.wish.datasource.WISH_TABLE
import ru.endroad.econom.component.wish.model.Importance
import ru.endroad.econom.component.wish.model.Wish

const val SQL_INSERT_WISH = "INSERT INTO $WISH_TABLE (name, cost, importance, info, complete) VALUES(?,?,?,?,?)"
const val SQL_UPDATE_WISH = "UPDATE $WISH_TABLE SET name = ?,cost = ?,importance = ?,info= ?,complete = ? WHERE id = ?"

val Wish.arrayValues get() = arrayOf(name, cost, importance, info, complete)

fun SupportSQLiteDatabase.mockWishes() = runTransaction {
	wishes.forEach { wish -> execSQL(SQL_INSERT_WISH, wish.arrayValues) }
}

val wishes = mutableListOf(
	Wish(name = "Автомобиль Lexus", cost = 400000, importance = Importance.LITTLE, info = "Lexus GS300, на коже с черным салоном"),
	Wish(name = "Велосипед", cost = 30000, importance = Importance.LITTLE, info = "На лето хорошо бы купить велосипед"),
	Wish(name = "Аккумулятор", importance = Importance.CRITICAL, cost = 4000),
	Wish(name = "Передние развальные рычаги", cost = 8000, info = "Hardrace, на заказ"),
	Wish(name = "Задние развальные рычаги", cost = 8000, info = "Hardrace, на заказ"),
	Wish(name = "X-bar", cost = 14000, info = "На заказ из челябинска"),
	Wish(name = "Проставки на колеса", cost = 5000, info = "5-10мм"),
	Wish(name = "Вакуумник 1 дюйм", importance = Importance.LITTLE, cost = 1500),
	Wish(name = "Распределение тормозного усилия", cost = 1500),
	Wish(name = "Ремкоплект на передние тормоза", cost = 3000, importance = Importance.CRITICAL, info = "Партномер: 01463-S04-000"),
	Wish(name = "Задние дисковые тормоза", cost = 10000, importance = Importance.BIG, info = "Можно купить у Сани"),
	Wish(name = "Cut Fender", cost = 4000, info = "На заказ от RT-Honda"),
	Wish(name = "Комплект передних фар", cost = 6000, importance = Importance.BIG, info = "На заказ из Питера"),
	Wish(name = "Колесные диски", importance = Importance.LITTLE, cost = 50000),
	Wish(name = "Сцепление/Выжимной подшипник", importance = Importance.LITTLE, cost = 3000),
	Wish(name = "Лобовое стекло", importance = Importance.LITTLE, cost = 7000)
)

val wishesLastPosition = wishes.size - 1
