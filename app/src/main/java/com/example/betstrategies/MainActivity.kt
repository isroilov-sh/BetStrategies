package com.example.betstrategies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.betstrategies.DataBase.allIds

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainDataAdapter
    private lateinit var dataManager: DataManager

    private val mainData = DataBase.table

    private val favorData = arrayListOf<Data>()

    private var isOnFavor = false

    override fun onResume() {
        updateData()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataManager = DataManager(baseContext)
        recyclerView = findViewById(R.id.rvMain)

        for ((index, prefsDatum) in dataManager.getPrefsData(allIds).withIndex()) {
            mainData[index].isFavor = prefsDatum
        }

        findViewById<ImageView>(R.id.ivSwitchToFavor).apply {
            setOnClickListener {
                isOnFavor = !isOnFavor
                setImageResource(if (isOnFavor) R.drawable.ic_favor_filled else R.drawable.ic_favor_stroke)
                if (isOnFavor) {
                    favorData.clear()
                    favorData.addAll(mainData.filter { it.isFavor })
                    adapter.setData(favorData)
                } else {
                    adapter.setData(mainData)
                }

            }
        }

        adapter = MainDataAdapter(mainData, object : MainDataAdapter.MainDataAdapterListener {
            override fun readMoreClick(id: String) {
                val infoActivity = Intent(baseContext, InfoActivity::class.java)
                infoActivity.putExtra("id", id)
                startActivity(infoActivity)
            }

            override fun favoriteClick(id: String) {
                dataManager.updatePrefsData(id, mainData.find { it.id == id }?.isFavor == true)
                updateData()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = adapter
    }

    fun updateData() {
        for ((index, prefsDatum) in dataManager.getPrefsData(allIds).withIndex()) {
            mainData[index].isFavor = prefsDatum
        }
        favorData.clear()
        favorData.addAll(mainData.filter { it.isFavor })
        adapter.setData(mainData)
    }
}