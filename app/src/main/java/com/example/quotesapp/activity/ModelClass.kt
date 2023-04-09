package com.example.quotesapp.activity

data class ModelClass (val id : Int,val name : String)

data class quotesmodel(val id: Int, val quotes: String, val categorytype: Int,var status : Int)

data class favouritemodelclass(val id: Int, val quotes: String, var status : Int )