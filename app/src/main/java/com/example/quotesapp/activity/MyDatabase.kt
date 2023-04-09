package com.example.quotesapp.activity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.math.log

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val mDataBase: SQLiteDatabase? = null
    private var mNeedUpdate = false
    private val mContext: Context

    init {
        if (Build.VERSION.SDK_INT >= 17) DB_PATH =
            context.applicationInfo.dataDir + "/databases/" else DB_PATH =
            "/data/data/" + context.packageName + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    //    TODO copy file
    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput = mContext.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    //    TODO update database
    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            val dbFile = File(DB_PATH + DB_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false
        }
    }

    @Synchronized
    override fun close() {
        mDataBase?.close()
        super.close()
    }

    fun readData() : ArrayList<ModelClass> {
        var databaselist = ArrayList<ModelClass>()
        val db = readableDatabase
        val sql = "select * from CategoryTb"
        val c = db.rawQuery(sql, null)
        if (c.moveToFirst()) {
            do {
                val id = c.getInt(0)
                val name = c.getString(1)
                Log.e(TAG, "readData:==> $id   $name ")
                var model = ModelClass(id,name)
                databaselist.add(model)
            } while (c.moveToNext())
        }
        return databaselist
    }


    fun quotesdata(c_id: Int): ArrayList<quotesmodel> {
        var quoteslistdis = ArrayList<quotesmodel>()

        val dbtwo = readableDatabase
        val sqltwo = "select * from QuotesTb where Categoryid= '$c_id'"
        val d = dbtwo.rawQuery(sqltwo,null)

        if (d.count > 0){
            d.moveToFirst()
            do {
                var id = d.getInt(0)
                var Quotes = d.getString(1)
                var Categoryid = d.getInt(2)
                var status = d.getInt(3)

                Log.e(TAG,"quotesdisplaydata :$id $Quotes")
                var shayrimodel = quotesmodel(id,Quotes,Categoryid,status)
                quoteslistdis.add(shayrimodel)
            }while (d.moveToNext())
        }


        return quoteslistdis
    }

    fun update_data(id: Int, status: Int){
        val update = writableDatabase
        val updatesql =
            "update QuotesTb set Status='$status' where id = '$id' "
        update.execSQL(updatesql)
    }

  fun display_status():ArrayList<favouritemodelclass>{
        var displayfavquotes = ArrayList<favouritemodelclass>()

      val displaydb = readableDatabase
      val displaysql = "select * from QuotesTb where Status = 1"
      val cursor = displaydb.rawQuery(displaysql,null)
      displaydb.rawQuery(displaysql,null)

      if (cursor.count > 0){
          cursor.moveToFirst()
          do {
              var id = cursor.getInt(0)
              var quotes = cursor.getString(1)
              var status = cursor.getInt(2)

              Log.e(TAG,"displayingData :  $id $quotes")
              var favouritemodelclass = favouritemodelclass(id,quotes,status)

              displayfavquotes.add(favouritemodelclass)
          } while (cursor.moveToNext())
      }
      return displayfavquotes
  }

    companion object {
        private const val TAG = "MyDatabase"
        private const val DB_NAME = "Quotesdb.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 1
    }
}