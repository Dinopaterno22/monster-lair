package de.enduni.monsterlair.common.datasource.statblocks

import de.enduni.monsterlair.common.datasource.statblocks.StatblockDto

interface StatblockDataSource {
    suspend fun getStatblocks(): List<StatblockDto>
}