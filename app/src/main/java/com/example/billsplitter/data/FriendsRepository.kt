package com.example.billsplitter.data

import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    fun getAllFriends() : Flow<List<Friend>>

    fun getFriendStream(id : Int) : Flow<Friend?>

    suspend fun insertFriend(friend: Friend)

    suspend fun deleteFriend(friend: Friend)

    suspend fun updateFriend(friend: Friend)
}