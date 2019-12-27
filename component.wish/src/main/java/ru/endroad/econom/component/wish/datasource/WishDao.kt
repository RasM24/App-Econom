package ru.endroad.econom.component.wish.datasource

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.component.wish.model.WishList

const val WISH_TABLE = "wishes"

@Dao
interface WishDao {

	@Query("SELECT * FROM wishes")
	fun getList(): Flow<WishList>

	@Query("SELECT * FROM wishes WHERE id=:id")
	suspend fun get(id: Int): Wish

	@Update
	suspend fun update(wish: Wish)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(wish: Wish)

	@Delete
	suspend fun delete(wish: Wish)
}