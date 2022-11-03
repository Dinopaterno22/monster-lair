package de.enduni.monsterlair.statblocks.persistence

import de.enduni.monsterlair.common.datasource.hazard.HazardDto
import de.enduni.monsterlair.common.datasource.monsters.MonsterDto
import de.enduni.monsterlair.common.datasource.statblocks.StatblockDto
import de.enduni.monsterlair.common.persistence.HazardEntity
import de.enduni.monsterlair.common.persistence.MonsterEntity
import de.enduni.monsterlair.common.persistence.MonsterTrait
import de.enduni.monsterlair.common.persistence.StatblockEntity
//import de.enduni.monsterlair.common.persistence.StatblockEntity
//import de.enduni.monsterlair.common.persistence.StatblockTrait
//import de.enduni.monsterlair.common.persistence.StatblockWithTraits
import de.enduni.monsterlair.monsters.domain.Monster

//TODO: Adicionar restantes partes do DTO
class StatblockEntityMapper {
    fun fromDtoToEntity(dto: StatblockDto) =
        StatblockEntity(
//            id = dto.id,
            name = dto.name
//            url = dto.url,
//            level = dto.level,
//            complexity = dto.complexity,
//            rarity = dto.rarity,
//            source = dto.source,
//            sourceType = dto.sourceType,
//            description = dto.description
        )
}