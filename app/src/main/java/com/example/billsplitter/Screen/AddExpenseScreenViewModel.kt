package com.example.billsplitter.Screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.exp

class AddExpenseScreenViewModel : ViewModel() {


    private var _expenseFriends = MutableStateFlow(
        mutableListOf<Friend>()
    )
//    private var _expenseFriends = MutableStateFlow(
//        mutableListOf<Friend>(
//            Friend("Arvind", 0),
//            Friend("Diljit", 1),
//            Friend("Dosanjh", 2),
//            Friend("Meena", 4),
//            Friend("Karmine", 5)
//        )
//    )
    var expenseShares: MutableMap<Int, Float> = mutableMapOf()
    var currentChecklist: MutableMap<Int, Boolean> = mutableMapOf()
    var expenseFriends = _expenseFriends.asStateFlow()

    init {
        if (_expenseFriends.value.size != expenseShares.size) {
            Log.d("INIT FOR EXPENSES", "Affirmative")
            for (i in expenseFriends.value) {
                expenseShares[i.frenId] = 0f
            }
        }
        if (currentChecklist.size != expenseFriends.value.size) {
            currentChecklist.putAll(expenseFriends.value.map { Pair(it.frenId, false) })
        }
    }

    fun updateFriendList(friendsList: List<Friend>, selectedFriend: MutableSet<Int>) {
        val tempList = mutableListOf<Friend>()
        expenseShares.clear()
        for (i in friendsList) {
            if (selectedFriend.contains(i.frenId)) {
                tempList.add(i)
                expenseShares[i.frenId] = 0f
            }
        }
        _expenseFriends.update {
            tempList
        }
    }

    fun updateChecklist(value : Boolean, frenId : Int){
        currentChecklist[frenId] = value
    }

    fun splitAExpense(amount: Float) {
//        val totalMembers = expenseShares.size
//        val share = amount / totalMembers
//        for (i in expenseShares) {
//            expenseShares[i.key] = i.value + share
//        }

        var totalMembers = 0
        for(i in currentChecklist){
            if(i.value)
                totalMembers += 1
        }
        val share = amount / totalMembers
        for(i in expenseShares){
            if(currentChecklist[i.key] == true){
                expenseShares[i.key] = i.value + share
            }
        }
    }


    fun getFinishedSplit(): String {
        var split = ""
        for (i in _expenseFriends.value) {
            split += i.name
            split += ": "
            split += expenseShares[i.frenId]
            split += "\n"
        }
        return split
    }

    //Used For checking if the checklist is updating correctly
    fun getChecklist() : String {
        var split = ""
        for (i in currentChecklist) {
            split += i.key
            split += ": "
            split += i.value
            split += "\n"
        }
        return split
    }



}