package com.example.billsplitter

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.billsplitter.Screen.SelectUserScreenViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SelectUserScreenViewModel(billSplitterApplication().container.friendsRepository)
        }
    }
}

fun CreationExtras.billSplitterApplication(): BillSplitterApplication =
(this[AndroidViewModelFactory.APPLICATION_KEY] as BillSplitterApplication)