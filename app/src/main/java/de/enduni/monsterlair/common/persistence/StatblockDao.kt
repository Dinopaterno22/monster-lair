package de.enduni.monsterlair.common.persistence

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface StatblockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatblocks(statblock: List<StatblockEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatblock(statblock: StatblockEntity)

    @Transaction
    @Query("SELECT * FROM statblocks WHERE name = :name LIMIT 1")
    suspend fun getStatblock(name: String): StatblockEntity
}
