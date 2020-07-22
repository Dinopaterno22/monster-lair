package de.enduni.monsterlair.creator.domain

import de.enduni.monsterlair.common.getXp
import de.enduni.monsterlair.creator.view.DangerType
import de.enduni.monsterlair.creator.view.EncounterCreatorFilter
import de.enduni.monsterlair.encounters.domain.model.Encounter
import de.enduni.monsterlair.encounters.domain.model.HazardWithRole
import de.enduni.monsterlair.encounters.persistence.HazardWithRoleMapper
import de.enduni.monsterlair.hazards.persistence.HazardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrieveHazardsWithRoleUseCase(
    private val repository: HazardRepository,
    private val hazardWithRoleMapper: HazardWithRoleMapper
) {

    suspend fun execute(
        filter: EncounterCreatorFilter,
        encounter: Encounter
    ) = withContext(Dispatchers.Default) {
        if (filter.dangerTypes.isNotEmpty() && !filter.dangerTypes.contains(DangerType.HAZARD)) {
            return@withContext emptyList<HazardWithRole>()
        }
        repository.getFilteredHazards(
            filter.string ?: "",
            filter.complexities,
            emptyList(),
            filter.lowerLevel,
            filter.upperLevel,
            filter.sortBy.getStringForHazard(),
            emptyList()
        )
            .map { hazardWithRoleMapper.mapToHazardWithRole(it, encounter.level) }
            .filterHazardWithinBudget(filter = filter.withinBudget, encounter = encounter)
    }

    private fun List<HazardWithRole>.filterHazardWithinBudget(
        filter: Boolean,
        encounter: Encounter
    ): List<HazardWithRole> {
        return if (filter) {
            val maxXp = encounter.targetBudget - encounter.currentBudget
            this.filter { it.role.getXp(it.complexity) <= maxXp }
        } else {
            this
        }
    }
}

