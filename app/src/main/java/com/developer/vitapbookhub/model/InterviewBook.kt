package com.developer.vitapbookhub.model

data class InterviewBook (
    val id:Int,
    var topic:String,
    var difficulty:String,
    var url:String

)
{
    constructor():this(0,"","","")
}
