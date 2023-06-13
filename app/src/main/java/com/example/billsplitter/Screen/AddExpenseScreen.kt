package com.example.billsplitter.Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.billsplitter.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    addExpenseScreenViewModel: AddExpenseScreenViewModel
) {

    val expenseFriends = addExpenseScreenViewModel.expenseFriends.collectAsState()

    Log.d("AddExpenseScreen", expenseFriends.value.toList().toString())
    var amount = remember {
        mutableStateOf("")
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Add Item")
        Row(
            modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Enter Amount: ")
            TextField(
                value = amount.value, onValueChange = {
                    amount.value = it
                }, modifier = modifier
                    .padding(8.dp)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Button(onClick = {
            navController.popBackStack(Screens.SELECT_USER_SCREEN.name, false)
        }, modifier = modifier.padding(8.dp)) {
            Text("Go Back")
        }
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(expenseFriends.value) { item ->
                var checked = remember {
                    mutableStateOf(false)
                }
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(item.name)
                    Checkbox(checked = checked.value, onCheckedChange = {
                        checked.value = it
                    })
                }
            }
        }
    }
}