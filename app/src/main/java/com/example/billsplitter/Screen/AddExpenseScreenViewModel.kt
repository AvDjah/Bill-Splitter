package com.example.billsplitter.Screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsplitter.data.ExpenseRepository
import com.example.billsplitter.models.Expense
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddExpenseScreenViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {


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
    private var expenseShares: MutableMap<Int, Float> = mutableMapOf()
    var currentChecklist: MutableMap<Int, Boolean> = mutableMapOf()
    var expenseFriends = _expenseFriends.asStateFlow()
    var currentExpenseId: Int = -1


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


    fun setExpense(
        id: Int,
        amount: Float,
        map: MutableMap<Int, Float>,
        names: MutableMap<Int, String>
    ) {
        Log.d("From Set expense", names.toString())
        currentExpenseId = id
        expenseShares = map
        currentChecklist.clear()
        currentChecklist.putAll(expenseShares.map {
            Pair(it.key, false)
        })
        expenseFriends.value.clear()
        expenseFriends.value.addAll(expenseShares.map {
            Friend(name = names[it.key] ?: "No Name", frenId = it.key)
        })
    }


    fun addFinishedMap(amount: Float) {
        var jsonMap = Json.encodeToString(expenseShares)

        var amt = 0f
        for (i in expenseShares) {
            amt += i.value
        }

        if (currentExpenseId != -1) {
            Log.d("CURRENT expense id", currentExpenseId.toString())
            viewModelScope.launch {
                val id = expenseRepository.updateExpense(
                    Expense(
                        amount = amt,
                        expenseShares = jsonMap,
                        id = currentExpenseId
                    )
                )
            }
        } else {
            viewModelScope.launch {
                val id =
                    expenseRepository.insertExpense(Expense(amount = amt, expenseShares = jsonMap))
                currentExpenseId = id.toInt()
                Log.d("NEW RECORD", id.toString())
            }
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

    fun updateChecklist(value: Boolean, frenId: Int) {
        currentChecklist[frenId] = value
    }

    fun splitAExpense(amount: Float) {
//        val totalMembers = expenseShares.size
//        val share = amount / totalMembers
//        for (i in expenseShares) {
//            expenseShares[i.key] = i.value + share
//        }

        var totalMembers = 0
        for (i in currentChecklist) {
            if (i.value)
                totalMembers += 1
        }
        val share = amount / totalMembers
        for (i in expenseShares) {
            if (currentChecklist[i.key] == true) {
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
    fun getChecklist(): String {
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