package com.example.billsplitter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.billsplitter.models.Expense
import com.example.billsplitter.models.ExpenseDao
import com.example.billsplitter.models.FriendDAO

@Database(entities = [Expense::class], version = 3, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase(){
    abstract fun expenseDao() : ExpenseDao

    companion object {
        @Volatile
        private var Instance : ExpenseDatabase? = null
        fun getDatabase(context : Context) : ExpenseDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,ExpenseDatabase::class.java,"expense_database")
                    .fallbackToDestructiveMigration().build().also {
                        Instance = it
                    }
            }
        }
    }
}