package com.example.billsplitter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.billsplitter.models.Friend
import com.example.billsplitter.models.FriendDAO

@Database(entities = [Friend::class], version = 1, exportSchema = false)
abstract class FriendsDatabase : RoomDatabase() {
    abstract fun friendDao() : FriendDAO

    companion object {
        @Volatile
        private var Instance : FriendsDatabase? = null
        fun getDatabase(context : Context) : FriendsDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,FriendsDatabase::class.java,"friends_database")
                    .fallbackToDestructiveMigration().build().also {
                        Instance = it
                    }
            }
        }
    }
}