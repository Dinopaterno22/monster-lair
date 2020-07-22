package de.enduni.monsterlair.creator.view

import de.enduni.monsterlair.common.domain.Complexity
import de.enduni.monsterlair.common.domain.MonsterType
import de.enduni.monsterlair.common.domain.SortBy
import de.enduni.monsterlair.encounters.domain.model.Encounter
import de.enduni.monsterlair.monsters.domain.Monster
import de.enduni.monsterlair.monsters.domain.MonsterFilter


data class EncounterCreatorDisplayState(
    val encounterName: String,
    val list: List<EncounterCreatorDisplayModel> = listOf(),
    val filter: EncounterCreatorFilter? = null,
    val error: EncounterCreatorError? = null
)

enum class EncounterCreatorError {
    RANDOM_ENCOUNTER_ERROR
}

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
    object CustomMonsterClicked : EncounterCreatorAction()
    class DangerAdded(val name: String) : EncounterCreatorAction()
    object EncounterSaved : EncounterCreatorAction()
    object ScrollUp : EncounterCreatorAction()
    object ShowCreatorHint : EncounterCreatorAction()
    class OnCustomMonsterPressed(val id: String, val monsterName: String) : EncounterCreatorAction()
    class OnEditCustomMonsterClicked(val monster: Monster) : EncounterCreatorAction()
    class OnGiveTreasureRecommendationClicked(val htmlTemplate: String) : EncounterCreatorAction()

}