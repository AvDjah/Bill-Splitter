package com.example.billsplitter.data

import com.example.billsplitter.models.Expense
import com.example.billsplitter.models.ExpenseDao
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    fun getAllExpense() : Flow<List<Expense>> = expenseDao.getAllExpense()

    fun getExpenseStream(id : Int) : Flow<Expense?> = expenseDao.getExpense(id)

    suspend fun insertExpense(expense: Expense) : Long = expenseDao.insert(expense)

    suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
}