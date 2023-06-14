package com.example.billsplitter.Screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsplitter.data.FriendsRepository
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SelectUserScreenViewModel(private val friendsRepository: FriendsRepository) : ViewModel() {


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val friendsUiList: StateFlow<SelectUserScreenState> = friendsRepository.getAllFriends().map {
        SelectUserScreenState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = SelectUserScreenState()
    )

    suspend fun deleteFriend(friend : Friend ){
        friendsRepository.deleteFriend(friend)
    }

    private val _friendsList = MutableStateFlow(
        mutableStateListOf<Friend>(
        )
    )
    val friendsList = _friendsList.asStateFlow()
    private var friendId = 0
    fun addUser(name: String) {
        if (name.isEmpty()) {
            return
        }
        var trimmedName = name.trim()
        _friendsList.update {
            it.add(Friend(trimmedName, ++friendId))
            it
        }
    }

    suspend fun saveFriend(friend: Friend) {
        friendsRepository.insertFriend(friend)
    }
}

data class SelectUserScreenState(
    val friendsList: List<Friend> = listOf()
)