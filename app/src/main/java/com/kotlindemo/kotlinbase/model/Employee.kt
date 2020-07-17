package com.kotlindemo.kotlinbase.model

import com.google.gson.annotations.SerializedName

data class Employee(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Data(

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("salary")
    var salary: String? = null,

    @field:SerializedName("age")
    var age: String? = null
)
