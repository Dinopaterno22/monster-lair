package de.enduni.monsterlair.statblocks.view

import de.enduni.monsterlair.monsters.view.MonsterOverviewAction
import de.enduni.monsterlair.statblocks.domain.Statblock

sealed class StatblockOverviewAction {
    class OnStatblockUpdated(val statblock: Statblock) : StatblockOverviewAction()
}