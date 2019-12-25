package ru.endroad.econom.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.endroad.econom.entity.Wish
import ru.endroad.econom.presenter.WishList

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