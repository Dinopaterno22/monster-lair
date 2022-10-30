package de.enduni.monsterlair.monsters.domain

import de.enduni.monsterlair.common.domain.*

data class Monster_Statblock(
    val id: String,
    val name: String,
    val level: Int,
    val alignment: Alignment,
    val traits: List<String>,
    val perception: Int,
    val senses: List<String>,
    val languages: List<String>,
//    val Skills: List<>
    val abilityMods: List<Int>,
    val items: List<String>,
//    val speed: List<>?
//    val attacks: List<>,
//    val spellcasting: List<>,
//    val abilities: List<>,
    val ac: Int,
    val savingThrows: List<Int>,
    val hp: Int,
)

