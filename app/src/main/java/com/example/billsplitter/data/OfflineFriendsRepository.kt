package com.example.billsplitter.data

import com.example.billsplitter.models.Friend
import com.example.billsplitter.models.FriendDAO
import kotlinx.coroutines.flow.Flow

class OfflineFriendsRepository(private val friendDAO: FriendDAO) : FriendsRepository {
    override fun getAllFriends(): Flow<List<Friend>> = friendDAO.getAllFriends()

    override fun getFriendStream(id: Int): Flow<Friend?> = friendDAO.getFriend(id)

    override suspend fun insertFriend(friend: Friend) : Long = friendDAO.insert(friend)

    override suspend fun deleteFriend(friend: Friend) = friendDAO.delete(friend)

    override suspend fun updateFriend(friend: Friend) = friendDAO.update(friend)

    override fun getFriendName(id: Int): Flow<String?> = friendDAO.getFriendName(id)


}