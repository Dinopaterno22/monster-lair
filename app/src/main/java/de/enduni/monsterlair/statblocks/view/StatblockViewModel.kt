package de.enduni.monsterlair.statblocks.view

import androidx.lifecycle.*
import de.enduni.monsterlair.common.view.ActionLiveData
import de.enduni.monsterlair.creator.domain.RandomEncounterException
import de.enduni.monsterlair.creator.view.EncounterCreatorAction
import de.enduni.monsterlair.monsters.persistence.MonsterRepository
import de.enduni.monsterlair.monsters.view.MonsterOverviewAction
import de.enduni.monsterlair.statblocks.domain.RetrieveStatblockUseCase
import de.enduni.monsterlair.statblocks.domain.Statblock
import de.enduni.monsterlair.statblocks.persistence.StatblockRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

// TODO: Clean Timber references
@ExperimentalCoroutinesApi
class StatblockViewModel(
    private val retrieveStatblockUseCase: RetrieveStatblockUseCase,
    private val statblockRepository: StatblockRepository
) : ViewModel() {

    private val _actions = ActionLiveData<StatblockOverviewAction>()
    val actions: LiveData<StatblockOverviewAction> get() = _actions

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Caught exception")
    }

    var statblock = Statblock("")

    fun updateStatblock(name: String) {
        Timber.d("Querying database for ${name}.")
        viewModelScope.launch(handler) {
            statblock = retrieveStatblockUseCase.execute(name)
            Timber.d("Result: ${statblock.name}")
            _actions.sendAction(StatblockOverviewAction.OnStatblockUpdated(statblock))
        }
    }
}

