package de.enduni.monsterlair.common.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.enduni.monsterlair.common.persistence.*


//TODO: Adicionar merdas do statblock aqui
@Database(
    entities = [
        HazardEntity::class,
        MonsterEntity::class,
        StatblockEntity::class,
        TreasureEntity::class,
        EncounterEntity::class,
        MonsterTrait::class,
        HazardTrait::class,
        TreasureTrait::class,
        MonsterAndTraitsCrossRef::class,
        HazardsAndTraitsCrossRef::class,
        TreasureAndTraitsCrossRef::class,
        MonsterForEncounterEntity::class,
        HazardForEncounterEntity::class],
    version = 4
)
abstract class MonsterDatabase : RoomDatabase() {

    abstract fun monsterDao(): MonsterDao

    abstract fun statblockDao(): StatblockDao

    abstract fun encounterDao(): EncounterDao

    abstract fun hazardDao(): HazardDao

    abstract fun treasureDao(): TreasureDao

    companion object {
        fun buildDatabase(context: Context): MonsterDatabase {
            return if (false) {
                Room.inMemoryDatabaseBuilder(context, MonsterDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .build()
            } else {
                Room.databaseBuilder(context, MonsterDatabase::class.java, "MonsterDatabase")
                    .addMigrations(IdentifierMigration(context))
                    .addMigrations(DescriptionMigration())
                    .addMigrations(StatblockMigration(context))
                    .build()
            }
        }

    }
}