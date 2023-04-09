package com.example.quotesapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.quotesapp.R

class FlashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_screen)
        Handler().postDelayed({
            val logo = Intent(this@FlashScreenActivity, MainActivity::class.java)
            startActivity(logo)
            finish()
        }, 4000)
    }
}