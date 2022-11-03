package de.enduni.monsterlair.common.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.enduni.monsterlair.common.domain.*
import de.enduni.monsterlair.common.persistence.database.EnumTypeConverters

@Entity(tableName = "statblocks")
@TypeConverters(EnumTypeConverters::class)
data class StatblockEntity(
//    @PrimaryKey val id: String,
    @PrimaryKey val name: String
//    val url: String,
//    val family: String,
//    val level: Int,
//    val alignment: Alignment,
//    val type: MonsterType,
//    val rarity: Rarity,
//    val size: Size,
//    val source: String,
//    val sourceType: Source,
//    val description: String
)