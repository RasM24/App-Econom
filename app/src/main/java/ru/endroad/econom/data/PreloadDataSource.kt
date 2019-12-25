package ru.endroad.econom.data

import androidx.sqlite.db.SupportSQLiteDatabase
import ru.endroad.arena.data.runTransaction
import ru.endroad.econom.data.room.ESTIMATION_TABLE
import ru.endroad.econom.entity.Estimation

object PreloadDataSource {

	fun SupportSQLiteDatabase.loadEstimationsData() = runTransaction {
		estiminations.forEachIndexed { index, estimation ->
			execSQL("INSERT INTO $ESTIMATION_TABLE (id, message, moneyRate) VALUES(?,?,?)", arrayOf(index, estimation.message, estimation.moneyRate))
		}
	}

	private val estiminations = listOf(
		Estimation(message = "На эти деньги можно купить %d пачек лапши", moneyRate = 17f),
		Estimation(message = "На эти деньги можно купить %d пачек сигарет", moneyRate = 98f),
		Estimation(message = "Билл Гейтс накопил бы эту сумму за %d секунд", moneyRate = 7200f)
	)
}