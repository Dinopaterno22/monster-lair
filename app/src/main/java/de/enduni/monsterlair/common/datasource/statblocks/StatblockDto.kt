package de.enduni.monsterlair.common.datasource.statblocks

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.enduni.monsterlair.common.domain.*

@JsonClass(generateAdapter = true)
data class StatblockDto(
        @Json(name = "name") val name: String,
        @Json(name = "level") val level: Int
//        @Json(name = "alignment") val alignment: Alignment,
//        @Json(name = "type") val type: MonsterType,
//        @Json(name = "size") val size: Size,
//        @Json(name = "source") val source: String,
//        @Json(name = "sourceType") val sourceType: Source,
//        @Json(name = "rarity") val rarity: Rarity,
//        @Json(name = "traits") val traits: List<String>,
//        @Json(name = "description") val description: String
    )
