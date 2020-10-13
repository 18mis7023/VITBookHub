package com.developer.vitapbookhub.model

data class Book (
    //val bookid:Int,
    val bookName:String,
    val bookAuthor:String,
    val bookUrl:String,
    val bookimage:Int,
    val imageurl:String
    /*,
    val bookimg:String*/
)
{
constructor():this("","","",0,"")
}