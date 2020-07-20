package com.kotlindemo.kotlinbase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlindemo.kotlinbase.model.DataItem

@Database(entities = [(DataItem::class)], version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun employeeDao(): EmployeeDao

    companion object {
        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "kotlindemo")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sInstance!!
        }
    }
}
