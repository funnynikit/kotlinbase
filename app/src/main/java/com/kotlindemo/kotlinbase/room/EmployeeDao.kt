package com.kotlindemo.kotlinbase.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlindemo.kotlinbase.model.DataItem

@Dao
interface EmployeeDao {

    @get:Query("SELECT * FROM dataitem")
    val all: List<DataItem>

    @Query("SELECT * FROM dataitem WHERE id IN (:empId)")
    fun loadAllByIds(empId: String): List<DataItem>

    @Query("SELECT * FROM dataitem WHERE employee_name LIKE :empName AND " + "employee_salary LIKE :salary LIMIT 1")
    fun findByName(empName: String, salary : String): DataItem

    @Insert
    fun insertAll(empList: List<DataItem>)

    @Query("DELETE FROM dataitem")
    fun nukeTable()

    @Delete
    fun delete(emp: DataItem)
}