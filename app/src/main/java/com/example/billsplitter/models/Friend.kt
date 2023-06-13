package com.example.billsplitter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class Friend(
    val name : String = "",
    @PrimaryKey(autoGenerate = true)
    val frenId : Int
)

