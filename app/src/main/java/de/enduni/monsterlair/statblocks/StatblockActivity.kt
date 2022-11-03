package de.enduni.monsterlair.statblocks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.enduni.monsterlair.creator.EncounterCreatorActivity
import de.enduni.monsterlair.databinding.ActivityEncounterCreatorBinding
import de.enduni.monsterlair.databinding.ActivityStatblockBinding
import de.enduni.monsterlair.statblocks.view.StatblockViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class StatblockActivity : AppCompatActivity(){

    private lateinit var binding: ActivityStatblockBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityStatblockBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupToolbar()

        val name = intent.getStringExtra(NAME)

        name?.let { updateUI(it) }
    }

    private fun updateUI(name: String)
    {
        binding.Name.text = name
    }

//    private fun setupToolbar() {
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = intent.getStringExtra(EncounterCreatorActivity.EXTRA_ENCOUNTER_NAME)
//    }

    fun intent(context : Context): Intent {
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