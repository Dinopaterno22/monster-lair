package de.enduni.monsterlair.common.persistence.database

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber

// TODO: Clean Timber references
class StatblockMigration(context : Context) : Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Timber.v("Migration Started")
        createNewStatblocksTable(database)
        moveNewStatblockTable(database)
    }

    //TODO: Adicionar colunas as bases de dados.
    private fun moveNewStatblockTable(database: SupportSQLiteDatabase) {
        Timber.v("Dropping old statblocks")
        database.execSQL("Drop table IF EXISTS statblocks")
        Timber.v("Renaming new_statblocks to statblocks")
        database.execSQL("ALTER TABLE new_statblocks RENAME TO statblocks")
    }

    private fun createNewStatblocksTable(database: SupportSQLiteDatabase) {
        Timber.v("Creating new_statblocks")
        database.execSQL("CREATE TABLE IF NOT EXISTS `new_statblocks` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))")
    }

}