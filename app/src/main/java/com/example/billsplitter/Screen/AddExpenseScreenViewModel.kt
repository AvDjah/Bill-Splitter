package com.example.billsplitter.Screen

import androidx.lifecycle.ViewModel
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddExpenseScreenViewModel : ViewModel() {
    private var _expenseFriends = MutableStateFlow(mutableListOf<Friend>())
    var expenseFriends = _expenseFriends.asStateFlow()

    fun updateFriendList(friendsList : List<Friend>, selectedFriend: MutableSet<Int>){
        val tempList = mutableListOf<Friend>()
        for(i in friendsList){
            if(selectedFriend.contains(i.frenId)){
                tempList.add(i)
            }
        }
        _expenseFriends.update {
            tempList
        }
    }
}