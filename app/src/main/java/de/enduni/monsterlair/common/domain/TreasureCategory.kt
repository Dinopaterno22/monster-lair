package de.enduni.monsterlair.common.domain

enum class TreasureCategory(val permanentItem: Boolean) {
    ADVENTURING_GEAR(true),
    ALCHEMICAL_BOMBS(false),
    ALCHEMICAL_ELIXIRS(false),
    ALCHEMICAL_POISONS(false),
    ALCHEMICAL_TOOLS(false),
    DRUGS(false),
    ARMOR_MAGIC(true),
    ARMOR_PRECIOUS(true),
    ARMOR_SPECIFIC(true),
    ARTIFACTS(true),
    AMMUNITION(false),
    OILS(false),
    OTHER_CONSUMABLES(false),
    POTIONS(false),
    SCROLLS(false),
    TALISMANS(false),
    CURSED_ITEMS(true),
    HELD_ITEMS(true),
    INTELLIGENT_ITEMS(true),
    MATERIALS(true),
    RUNES_ARMOR_PROPERTY(true),
    RUNES_ARMOR_FUNDAMENTAL(true),
    RUNES_WEAPON_PROPERTY(true),
    RUNES_WEAPON_FUNDAMENTAL(true),
    SERVICES(false),
    SHIELDS_PRECIOUS(true),
    SHIELDS_SPECIFIC(true),
    SNARES(false),
    STAVES(true),
    STRUCTURES(true),
    TATTOOS(true),
    WANDS_MAGIC(true),
    WANDS_SPECIALTY(true),
    WEAPONS_MAGIC(true),
    WEAPONS_PRECIOUS(true),
    WEAPONS_SPECIFIC(true),
    WORN_APEX(true),
    WORN_COMPANION(true),
    WORN_OTHER(true)
}