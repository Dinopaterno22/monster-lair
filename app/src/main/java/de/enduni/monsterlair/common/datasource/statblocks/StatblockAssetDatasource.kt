package de.enduni.monsterlair.common.datasource.statblocks

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.enduni.monsterlair.R
import de.enduni.monsterlair.common.datasource.statblocks.StatblockDto
import de.enduni.monsterlair.common.datasource.typeadapters.EnumAdapter

class StatblockAssetDatasource(private val context: Context) :
    StatblockDataSource {

        private val jsonAdapter: JsonAdapter<List<StatblockDto>>

        init {
            val type = Types.newParameterizedType(List::class.java, StatblockDto::class.java)
            jsonAdapter = Moshi.Builder()
                .add(EnumAdapter())
                .build().adapter<List<StatblockDto>>(type)
        }

    override suspend fun getStatblocks(): List<StatblockDto> {
        val raw = context.resources.openRawResource(R.raw.statblocks)
        val json = String(raw.readBytes())
        val statblocks = jsonAdapter.fromJson(json)
        return statblocks?.let { it } ?: throw RuntimeException("Error loading statblocks")
    }
}