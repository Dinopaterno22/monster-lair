package de.enduni.monsterlair.encounters.creator.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.enduni.monsterlair.common.view.ActionLiveData
import de.enduni.monsterlair.encounters.creator.domain.RetrieveEncounterUseCase
import de.enduni.monsterlair.encounters.creator.domain.RetrieveHazardsWithRoleUseCase
import de.enduni.monsterlair.encounters.creator.domain.RetrieveMonstersWithRoleUseCase
import de.enduni.monsterlair.encounters.creator.domain.StoreEncounterUseCase
import de.enduni.monsterlair.encounters.creator.view.adapter.DangerForEncounterViewHolder
import de.enduni.monsterlair.encounters.creator.view.adapter.DangerViewHolder
import de.enduni.monsterlair.encounters.creator.view.adapter.EncounterBudgetViewHolder
import de.enduni.monsterlair.encounters.creator.view.adapter.EncounterDetailViewHolder
import de.enduni.monsterlair.encounters.domain.CalculateEncounterBudgetUseCase
import de.enduni.monsterlair.encounters.domain.model.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EncounterCreatorViewModel(
    private val retrieveMonstersWithRoleUseCase: RetrieveMonstersWithRoleUseCase,
    private val retrieveHazardsWithRoleUseCase: RetrieveHazardsWithRoleUseCase,
    private val calculateEncounterBudgetUseCase: CalculateEncounterBudgetUseCase,
    private val retrieveEncounterUseCase: RetrieveEncounterUseCase,
    private val mapper: EncounterCreatorDisplayModelMapper,
    private val storeEncounterUseCase: StoreEncounterUseCase
) : ViewModel(),
    EncounterBudgetViewHolder.OnSaveClickedListener,
    DangerViewHolder.DangerSelectedListener,
    DangerForEncounterViewHolder.DangerForEncounterListener,
    EncounterDetailViewHolder.OnNameChangedListener {

    private val _viewState = MutableLiveData<EncounterCreatorDisplayState>()
    val viewState: LiveData<EncounterCreatorDisplayState> get() = _viewState

    private val _actions = ActionLiveData<EncounterCreatorAction>()
    val actions: LiveData<EncounterCreatorAction> get() = _actions

    private var filter = EncounterCreatorFilter()

    private var monsters: List<MonsterWithRole> = listOf()

    private var hazards: List<HazardWithRole> = listOf()

    private lateinit var encounter: Encounter

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Caught exception")
    }

    fun start(
        numberOfPlayers: Int,
        levelOfEncounter: Int,
        targetDifficulty: EncounterDifficulty,
        encounterId: Long
    ) {
        if (viewState.value != null) {
            return
        }
        if (encounterId == -1L) {
            encounter = Encounter(
                numberOfPlayers = numberOfPlayers,
                level = levelOfEncounter,
                targetDifficulty = targetDifficulty
            )
            val filter = EncounterCreatorFilter(
                lowerLevel = levelOfEncounter - 4,
                upperLevel = levelOfEncounter + 4
            )
            viewModelScope.launch(handler) { filterDangers(filter) }
        } else {
            viewModelScope.launch(handler) {
                encounter = retrieveEncounterUseCase.execute(encounterId)
                val filter = EncounterCreatorFilter(
                    lowerLevel = levelOfEncounter - 4,
                    upperLevel = levelOfEncounter + 4
                )
                filterDangers(filter)
            }
        }

    }

    fun filterByString(string: String) = viewModelScope.launch(handler) {
        filterDangers(filter.copy(string = string))
    }

    fun adjustFilterLevelLower(lowerLevel: Int) = viewModelScope.launch(handler) {
        filterDangers(filter.copy(lowerLevel = lowerLevel))
    }

    fun adjustFilterLevelUpper(upperLevel: Int) = viewModelScope.launch(handler) {
        filterDangers(filter.copy(upperLevel = upperLevel))
    }


    private suspend fun filterDangers(newFilter: EncounterCreatorFilter) {
        if (newFilter != filter) {
            filter = newFilter
            Timber.d("Starting monster filter with $newFilter")
            monsters = retrieveMonstersWithRoleUseCase.execute(newFilter, encounter.level)
            hazards = retrieveHazardsWithRoleUseCase.execute(newFilter, encounter.level)
            postCurrentState()
        }
    }

    private fun postCurrentState() = viewModelScope.launch(handler) {
        launch(Dispatchers.Default) {
            val budget = calculateEncounterBudgetUseCase.execute(encounter)
            val dangers = (monsters.toMonsterDisplayModel() +
                    hazards.toHazardDisplayModel()).sortedBy { it.name }
            val dangersForEncounter = (encounter.monsters.toEncounterMonsterDisplayModel() +
                    encounter.hazards.toEncounterHazardDisplayModel())
                .sortedBy { it.name }

            val list: List<EncounterCreatorDisplayModel> =
                encounter.toDetailDisplayModel() +
                        dangersForEncounter +
                        budget.toBudgetDisplayModel() +
                    dangers

            val state = EncounterCreatorDisplayState(
                list = list,
                filter = filter
            )
            _viewState.postValue(state)
        }
    }


    private fun List<EncounterMonster>.toEncounterMonsterDisplayModel(): List<EncounterCreatorDisplayModel.DangerForEncounter> {
        return this.map { mapper.toDanger(it) }
    }

    private fun List<EncounterHazard>.toEncounterHazardDisplayModel(): List<EncounterCreatorDisplayModel.DangerForEncounter> {
        return this.map { mapper.toDanger(it) }
    }

    private fun Encounter.toDetailDisplayModel(): List<EncounterCreatorDisplayModel> {
        return listOf(mapper.toDetails(this))
    }

    private fun EncounterBudget.toBudgetDisplayModel(): List<EncounterCreatorDisplayModel> {
        return listOf(mapper.toBudget(this))
    }

    private fun List<MonsterWithRole>.toMonsterDisplayModel(): List<EncounterCreatorDisplayModel.Danger> {
        return this.map { mapper.toDanger(it) }
    }

    private fun List<HazardWithRole>.toHazardDisplayModel(): List<EncounterCreatorDisplayModel.Danger> {
        return this.map { mapper.toDanger(it) }
    }

    override fun onDecrement(type: DangerType, id: Long) {
        when (type) {
            DangerType.MONSTER -> encounter.decrementCount(monsterId = id)
            DangerType.HAZARD -> encounter.decrementCount(hazardId = id)
        }
        postCurrentState()
    }

    override fun onIncrement(type: DangerType, id: Long) {
        when (type) {
            DangerType.MONSTER -> encounter.incrementCount(monsterId = id)
            DangerType.HAZARD -> encounter.incrementCount(hazardId = id)
        }
        postCurrentState()
    }

    override fun onDangerForEncounterSelected(type: DangerType, id: Long) {
        linkClicked(type, id)
    }

    override fun onDangerSelected(type: DangerType, id: Long) {
        linkClicked(type, id)
    }

    private fun linkClicked(type: DangerType, id: Long) {
        viewModelScope.launch(handler) {
            val url = when (type) {
                DangerType.MONSTER -> {
                    findMonsterWithId(id)?.url
                }
                DangerType.HAZARD -> {
                    findHazardWithId(id)?.url
                }
            }
            url?.let { _actions.sendAction(EncounterCreatorAction.DangerLinkClicked(it)) }
        }
    }

    override fun onSaveClicked() {
        _actions.sendAction(EncounterCreatorAction.SaveClicked(encounter.name))
    }

    override fun onAddClicked(type: DangerType, id: Long) {
        viewModelScope.launch(handler) {
            when (type) {
                DangerType.MONSTER -> {
                    findMonsterWithId(id)?.let {
                        encounter.addMonster(it)
                        _actions.postValue(EncounterCreatorAction.DangerAdded(it.name))
                    }
                }
                DangerType.HAZARD -> {
                    findHazardWithId(id)?.let {
                        encounter.addHazard(it)
                        _actions.postValue(EncounterCreatorAction.DangerAdded(it.name))
                    }
                }
            }
            postCurrentState()
        }
    }

    override fun onNameChanged(name: String) {
        encounter.name = name
    }

    fun saveEncounter() {
        viewModelScope.launch(handler) {
            encounter.name = if (encounter.name.isBlank()) "Random Encounter" else encounter.name
            storeEncounterUseCase.store(encounter)
            _actions.sendAction(EncounterCreatorAction.EncounterSaved)
        }
    }

    private suspend fun findMonsterWithId(id: Long): MonsterWithRole? =
        withContext(Dispatchers.Default) {
            monsters.find { it.id == id }
        }

    private suspend fun findHazardWithId(id: Long): HazardWithRole? =
        withContext(Dispatchers.Default) {
            hazards.find { it.id == id }
        }


}

