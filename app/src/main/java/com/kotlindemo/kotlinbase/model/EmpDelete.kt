package com.kotlindemo.kotlinbase.model

import com.google.gson.annotations.SerializedName

data class EmpDelete(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
