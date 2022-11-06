package de.enduni.monsterlair.statblocks.domain

import de.enduni.monsterlair.statblocks.persistence.StatblockRepository

class RetrieveStatblockUseCase (
    private val statblockRepository: StatblockRepository
        ){
    suspend fun execute(name: String) = statblockRepository.getStatblock(name)
}