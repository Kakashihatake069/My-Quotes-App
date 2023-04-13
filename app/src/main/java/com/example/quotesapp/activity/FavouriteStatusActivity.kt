package com.example.quotesapp.activity

import adapter.StatusAdapter
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.R
import com.example.quotesapp.databinding.ActivityFavouriteStatusBinding

class FavouriteStatusActivity : AppCompatActivity() {
    lateinit var favouriteBinding: ActivityFavouriteStatusBinding
    lateinit var statusdb: MyDatabase
    var statuslist = ArrayList<favouritemodelclass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouriteBinding = ActivityFavouriteStatusBinding.inflate(layoutInflater)
        setContentView(favouriteBinding.root)

        statusdb = MyDatabase(this)
        initview()


    }

    private fun initview() {
        statuslist = statusdb.display_status()


        var adapter = StatusAdapter({ id, status ->
            statusdb.update_data(id, status)
        })
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        favouriteBinding.rcvfavstatus.layoutManager = manager
        favouriteBinding.rcvfavstatus.adapter = adapter

        adapter.updatelist(statuslist)
        favouriteBinding.imglikeback.setOnClickListener {
            onBackPressed()
        }

    }

}