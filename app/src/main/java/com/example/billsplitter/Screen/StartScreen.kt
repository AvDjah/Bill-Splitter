package com.example.billsplitter.Screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen() {



    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("History")
            })
        }
    ) {
        StartScreenMain(paddingValues = it)
    }
}

@Composable
fun StartScreenMain(modifier: Modifier = Modifier, paddingValues: PaddingValues) {

}