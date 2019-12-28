package ru.endroad.birusa.feature.estimation.data

import ru.endroad.birusa.feature.estimation.Estimation

internal class EstimationDataSource {

	private val dataSource = listOf(Estimation(message = "На эти деньги можно купить %d пачек лапши",
																									   moneyRate = 17f),
									Estimation(message = "На эти деньги можно купить %d пачек сигарет",
																									   moneyRate = 98f),
									Estimation(message = "Билл Гейтс накопил бы эту сумму за %d секунд",
																									   moneyRate = 7200f))

	fun get(): List<Estimation> = dataSource
}