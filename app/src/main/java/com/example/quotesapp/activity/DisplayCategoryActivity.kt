package com.example.quotesapp.activity

import adapter.QuotesAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.R
import com.example.quotesapp.databinding.ActivityDisplayCategoryBinding

class DisplayCategoryActivity : AppCompatActivity() {

    lateinit var quotesBinding : ActivityDisplayCategoryBinding
    lateinit var displaydb : MyDatabase
    var quoteslist = ArrayList<quotesmodel>()
    lateinit var adapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_category)
        quotesBinding = ActivityDisplayCategoryBinding.inflate(layoutInflater)
        setContentView(quotesBinding.root)

        displaydb = MyDatabase(this)
        initview()
    }

    private fun initview() {

        quotesBinding.imgcategoryfav.setOnClickListener {
            var favbutton = Intent(this,FavouriteStatusActivity::class.java)
            startActivity(favbutton)
            finish()
        }


        var quotes : String? = intent.getStringExtra("Title")
        quotesBinding.txtquotestitle.text = quotes

        var id = intent.getIntExtra("id",0)
        quoteslist = displaydb.quotesdata(id)

        adapter = QuotesAdapter(this,quoteslist,{
            var display = Intent(this, DisplayImageActivity::class.java)
            display.putExtra("quotes",it.quotes)
            startActivity(display)

        },{id,fav->
                displaydb.update_data(id,fav)
            })
        var manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        quotesBinding.rcvdisplaycategory.layoutManager =manager
        quotesBinding.rcvdisplaycategory.adapter = adapter
    }

}