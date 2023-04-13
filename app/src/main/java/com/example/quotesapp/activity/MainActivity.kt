package com.example.quotesapp.activity

import adapter.CategroryAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.R
import com.example.quotesapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var adapter : CategroryAdapter
     var categorylist = ArrayList<ModelClass>()
        lateinit var categorydb : MyDatabase
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categorydb = MyDatabase(this)
        initview()
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    private fun initview() {

        binding.imgmenu.setOnClickListener {
            binding.navigationdrawer.openDrawer(GravityCompat.START)
        }

        binding.drawerfav.setOnClickListener {
           var linkfav =Intent(this,FavouriteStatusActivity::class.java)
            startActivity(linkfav)
        }

        binding.drawercategory.setOnClickListener {
            binding.navigationdrawer.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "you are already in category", Toast.LENGTH_SHORT).show()

        }
        binding.drawerexit.setOnClickListener {
            onBackPressed()
        }

        binding.drawerprivacy.setOnClickListener {
            var linkprivacy = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://akshaypatel4120.blogspot.com/2023/04/summary-of-changes-weve-updated-how-we.html")
            startActivity(linkprivacy)
        }

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