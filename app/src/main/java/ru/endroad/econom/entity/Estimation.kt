package ru.endroad.econom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.endroad.econom.data.room.ESTIMATION_TABLE

@Entity(tableName = ESTIMATION_TABLE) //TODO при необходимости разнести на сущность и pojo-модель
data class Estimation(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val message: String,
	val moneyRate: Float
)