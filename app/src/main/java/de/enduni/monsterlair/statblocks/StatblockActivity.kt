package de.enduni.monsterlair.statblocks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import de.enduni.monsterlair.creator.view.EncounterCreatorViewModel
import de.enduni.monsterlair.databinding.ActivityStatblockBinding
import de.enduni.monsterlair.statblocks.domain.Statblock
import de.enduni.monsterlair.statblocks.persistence.StatblockRepository
import de.enduni.monsterlair.statblocks.view.StatblockOverviewAction
import de.enduni.monsterlair.statblocks.view.StatblockViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
class StatblockActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityStatblockBinding

    private val viewModel: StatblockViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatblockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        Timber.d("Using name = $name")
        viewModel.actions.observe(this, Observer{handleAction(it)})
        name?.let { viewModel.updateStatblock(it) }
    }

    private fun updateUI(statblock: Statblock) {
        Timber.d("Using ${statblock.name} in the activity")
        binding.toolbar.title = statblock.name
    }

    private fun handleAction(action: StatblockOverviewAction?){
        when(action){
            is StatblockOverviewAction.OnStatblockUpdated->{
                updateUI(action.statblock)
            }
        }
    }

    fun intent(context: Context): Intent {
        return Intent(context, StatblockActivity::class.java)
    }

    companion object {

        private const val NAME = "name"

        fun intent(
            context: Context,
            name: String
        ): Intent {
            return Intent(context, StatblockActivity::class.java).apply {
                putExtra(NAME, name)
            }
        }

    }

}