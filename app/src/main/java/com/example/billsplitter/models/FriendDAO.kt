package com.example.billsplitter.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friend : Friend) : Long
    @Update
    suspend fun update(friend : Friend)
    @Delete
    suspend fun delete(friend: Friend)
    @Query("SELECT * from friends where frenId = :id")
    fun getFriend(id : Int) : Flow<Friend>
    @Query("SELECT * from friends")
    fun getAllFriends() : Flow<List<Friend>>
    @Query("SELECT name from friends where frenId = :id")
    fun getFriendName(id : Int) : Flow<String>
}