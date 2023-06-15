package com.example.billsplitter.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val friendsRepository : FriendsRepository
    val expenseRepository : ExpenseRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val friendsRepository: FriendsRepository by lazy {
        OfflineFriendsRepository(FriendsDatabase.getDatabase(context).friendDao())
    }
    override val expenseRepository: ExpenseRepository by lazy {
        ExpenseRepository(ExpenseDatabase.getDatabase(context).expenseDao())
    }
}