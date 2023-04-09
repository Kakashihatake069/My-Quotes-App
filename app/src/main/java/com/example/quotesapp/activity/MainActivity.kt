package com.example.quotesapp.activity

import adapter.CategroryAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.R
import com.example.quotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var adapter : CategroryAdapter
     var categorylist = ArrayList<ModelClass>()
        lateinit var categorydb : MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categorydb = MyDatabase(this)
        initview()
    }

    private fun initview() {
        categorylist = categorydb.readData()
        adapter = CategroryAdapter(categorylist){

            var i = Intent(this,DisplayCategoryActivity::class.java)
            i.putExtra("Title",it.name)
            i.putExtra("id",it.id)

            Log.e("TAG","data:" +it.id)
            startActivity(i)
        }


        var manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rcvmain.layoutManager = manager
        binding.rcvmain.adapter = adapter

    }


}