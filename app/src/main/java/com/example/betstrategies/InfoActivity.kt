package com.example.betstrategies

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.betstrategies.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding
    private lateinit var dataManager: DataManager
    private lateinit var id: String
    private var isFavor = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataManager = DataManager(baseContext)
        id = intent.getStringExtra("id").toString()
        isFavor = DataBase.table.find { it.id == id }?.isFavor == true
        setSupportActionBar(findViewById(R.id.toolbar))

        fabToggle()
        initViews()
    }

    private fun initViews() {
        DataBase.table.find { it.id == id }?.let {
            binding.toolbarLayout.title = it.title
            binding.toolbarLayout.setBackgroundResource(it.resourceId)
            findViewById<TextView>(R.id.tvContent).text = it.content

        }
        binding.fab.setOnClickListener { view ->
            isFavor = !isFavor
            dataManager.updatePrefsData(id, isFavor)
            fabToggle()
        }
    }

    private fun fabToggle() {
        binding.fab.setImageResource(
            if (isFavor)
                R.drawable.ic_favor_filled
            else R.drawable.ic_favor_stroke
        )
    }
}