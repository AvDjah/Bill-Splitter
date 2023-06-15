package com.example.billsplitter.Screen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsplitter.data.ExpenseRepository
import com.example.billsplitter.models.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StartScreenViewModel(private var expenseRepository: ExpenseRepository) : ViewModel() {
    private var _expenseList = expenseRepository.getAllExpense()
    var expenseList : StateFlow<List<Expense>> = _expenseList.map { it }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}