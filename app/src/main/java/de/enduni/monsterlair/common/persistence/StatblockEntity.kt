package de.enduni.monsterlair.common.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.enduni.monsterlair.common.domain.*
import de.enduni.monsterlair.common.persistence.database.EnumTypeConverters

@Entity(tableName = "statblocks")
@TypeConverters(EnumTypeConverters::class)
data class StatblockEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val level: Int
)