package com.example.billsplitter.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val friendsRepository : FriendsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val friendsRepository: FriendsRepository by lazy {
        OfflineFriendsRepository(FriendsDatabase.getDatabase(context).friendDao())
    }
}