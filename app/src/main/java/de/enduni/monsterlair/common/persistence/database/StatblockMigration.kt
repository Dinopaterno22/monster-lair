package de.enduni.monsterlair.common.persistence.database

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class StatblockMigration(context : Context) : Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        createNewStatblocksTable(database)
        moveNewStatblockTable(database)
    }

    //TODO: Adicionar colunas as bases de dados.
    private fun moveNewStatblockTable(database: SupportSQLiteDatabase) {
        database.execSQL("Drop table IF EXISTS statblocks")
        database.execSQL("ALTER TABLE new_statblocks RENAME TO statblocks")
    }

    private fun createNewStatblocksTable(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `new_statblocks` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))")
    }

}