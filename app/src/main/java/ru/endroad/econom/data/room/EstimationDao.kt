package ru.endroad.econom.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.endroad.econom.entity.Estimation

@Dao
interface EstimationDao {

	//TODO переделать запрос на getRandom
	@Query("SELECT * FROM estimations")
	suspend fun getList(): List<Estimation>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(estimation: Estimation)
}