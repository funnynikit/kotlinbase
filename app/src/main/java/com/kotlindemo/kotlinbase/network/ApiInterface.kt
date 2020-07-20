package com.kotlindemo.kotlinbase.network

import com.kotlindemo.kotlinbase.model.Data
import com.kotlindemo.kotlinbase.model.EmpDelete
import com.kotlindemo.kotlinbase.model.EmpResponse
import com.kotlindemo.kotlinbase.model.Employee
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("employees")
    fun getEmployees(): Call<EmpResponse>

    @GET("employees")
    fun getAllEmployees(): Observable<EmpResponse>

    @POST("create")
    fun createEmployee(@Body data: Data): Call<Employee>

    @DELETE("delete/{id}")
    fun deleteEmployee(@Path("id") empId: String?): Call<EmpDelete>

    @PUT("update/{id}")
    fun updateRecord(@Body emp: Data, @Path("id") empId: String?): Call<Employee>

}