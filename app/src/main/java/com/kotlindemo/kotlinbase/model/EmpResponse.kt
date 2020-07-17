package com.kotlindemo.kotlinbase.model

import com.google.gson.annotations.SerializedName

data class EmpResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataItem(

    @field:SerializedName("profile_image")
    val profileImage: String? = null,

    @field:SerializedName("employee_name")
    var employeeName: String? = null,

    @field:SerializedName("employee_salary")
    var employeeSalary: String? = null,

    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("employee_age")
    var employeeAge: String? = null
)
