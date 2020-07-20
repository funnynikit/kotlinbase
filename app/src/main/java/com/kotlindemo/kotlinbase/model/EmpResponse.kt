package com.kotlindemo.kotlinbase.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EmpResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

@Entity
data class DataItem(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: String,

    @ColumnInfo(name = "profile_image")
    @field:SerializedName("profile_image")
    val profileImage: String,

    @ColumnInfo(name = "employee_name")
    @field:SerializedName("employee_name")
    var employeeName: String,

    @ColumnInfo(name = "employee_salary")
    @field:SerializedName("employee_salary")
    var employeeSalary: String,

    @ColumnInfo(name = "employee_age")
    @field:SerializedName("employee_age")
    var employeeAge: String
)
