package com.example.billsplitter.Screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectUserScreenViewModel : ViewModel() {
    private val _friendsList  = MutableStateFlow(mutableStateListOf<Friend>(
    ))
    val friendsList = _friendsList.asStateFlow()
    private var friendId = 0
    fun addUser(name : String){
        if(name.isEmpty()){
            return
        }
        var trimmedName = name.trim()
        _friendsList.update {
            it.add(Friend(trimmedName,++friendId))
            it
        }
    }
}