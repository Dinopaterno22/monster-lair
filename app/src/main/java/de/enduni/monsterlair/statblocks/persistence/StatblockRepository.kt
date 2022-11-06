package de.enduni.monsterlair.statblocks.persistence

import de.enduni.monsterlair.common.persistence.StatblockDao
import de.enduni.monsterlair.statblocks.domain.Statblock

//TODO: Actualizar Query
class StatblockRepository (
    private val statblockDao: StatblockDao,
    private val statblockEntityMapper: StatblockEntityMapper
        ){

    suspend fun getStatblock(name: String) : Statblock = statblockDao.getStatblock(name)
}