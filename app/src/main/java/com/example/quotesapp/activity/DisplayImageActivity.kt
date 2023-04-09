package com.example.quotesapp.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.GroupAlertBehavior
import com.example.quotesapp.R
import com.example.quotesapp.databinding.ActivityDisplayImageBinding

class DisplayImageActivity : AppCompatActivity() {
    lateinit var displayBinding : ActivityDisplayImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)
        displayBinding = ActivityDisplayImageBinding.inflate(layoutInflater)
        setContentView(displayBinding.root)

        initview()

    }
    private fun initview() {
        var quotesname : String? = intent.getStringExtra("quotes")
        displayBinding.displaytext.text = quotesname

        displayBinding.imgbackbuttonofdis.setOnClickListener {
            var back = Intent(this,MainActivity::class.java)
            startActivity(back)
        }
       //Share Quotes  Method
        displayBinding.imgshare.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT,quotesname)
            startActivity(i)
        }

        // Copy Quotes Method
        displayBinding.imgcopy.setOnClickListener {
            val clipboard : ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",quotesname)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"copy code",Toast.LENGTH_SHORT).show()
        }

        // Pick Image Method
        displayBinding.addimage.setOnClickListener {
            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallery_Launcher.launch(Gallery)
        }
    }

    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val data = result.data
            val uri = data!!.data
            displayBinding.displayimage.setImageURI(uri)
        }
    }





}