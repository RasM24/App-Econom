package ru.endroad.econom.component.wish.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.component.wish.model.WishModel

const val WISH_TABLE = "wishes"

//TODO придумать, как сделать internal
@Dao
interface WishDao {

	@Query("SELECT * FROM wishes")
	fun getList(): Flow<List<WishModel>>

	@Query("SELECT * FROM wishes WHERE id=:id")
	suspend fun get(id: Int): WishModel

	@Update
	suspend fun update(wishModel: WishModel)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(wishModel: WishModel)

	@Delete
	suspend fun delete(wishModel: WishModel)
}