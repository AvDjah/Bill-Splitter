package com.example.billsplitter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var amount : Float,
    var expenseShares : String
)
