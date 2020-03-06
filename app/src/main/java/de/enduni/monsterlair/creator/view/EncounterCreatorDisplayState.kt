package de.enduni.monsterlair.creator.view

import de.enduni.monsterlair.common.domain.Complexity
import de.enduni.monsterlair.common.domain.MonsterType
import de.enduni.monsterlair.encounters.domain.model.Encounter
import de.enduni.monsterlair.monsters.view.MonsterFilter
import de.enduni.monsterlair.monsters.view.SortBy


data class EncounterCreatorDisplayState(
    val encounterName: String,
    val list: List<EncounterCreatorDisplayModel> = listOf(),
    val filter: EncounterCreatorFilter? = null
)

data class EncounterCreatorFilter(
    val string: String? = null,
    val lowerLevel: Int = MonsterFilter.DEFAULT_LEVEL_LOWER,
    val upperLevel: Int = MonsterFilter.DEFAULT_LEVEL_UPPER,
    val monsterTypes: List<MonsterType> = listOf(),
    val complexities: List<Complexity> = listOf(),
    val dangerTypes: List<DangerType> = listOf(),
    val sortBy: SortBy = SortBy.NAME,
    val withinBudget: Boolean = true
)

sealed class EncounterCreatorAction {

    class EditEncounterClicked(val encounter: Encounter) : EncounterCreatorAction()
    class DangerLinkClicked(val url: String) : EncounterCreatorAction()
    class DangerAdded(val name: String) : EncounterCreatorAction()
    object EncounterSaved : EncounterCreatorAction()
    object ScrollUp : EncounterCreatorAction()

}