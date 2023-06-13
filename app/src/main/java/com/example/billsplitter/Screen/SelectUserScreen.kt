package com.example.billsplitter.Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.billsplitter.Dialogs

@Composable
fun SelectUserScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectUserScreenViewModel: SelectUserScreenViewModel
) {
//    val selectUserScreenViewModel: SelectUserScreenViewModel = viewModel()
    val friendsList = selectUserScreenViewModel.friendsList.collectAsState()
    val showSelected = remember {
        mutableStateOf("")
    }

    var selectedFriends = remember {
        mutableStateOf(mutableSetOf<Int>())
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Select Friends")
        Button(onClick = {
            navController.navigate(Dialogs.ADD_FRIEND.name)
        }, modifier = modifier.padding(8.dp)) {
            Text(text = "Add Friend", textAlign = TextAlign.Center)
        }
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(friendsList.value) { item ->
                var checked = remember {
                    mutableStateOf(false)
                }
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.name)
                    Checkbox(checked = checked.value, onCheckedChange = {
                        checked.value = it
                        Log.d("StatusChange",item.name+"-> "+it.toString())
                        if (it) {
                            selectedFriends.value.add(item.frenId)
                        } else {
                            selectedFriends.value.remove(item.frenId)
                        }
                    })
                }
            }
        }
        Button(onClick = {
                         showSelected.value = ""
            val list = selectedFriends.value.toList().toString()
            showSelected.value = list
        },modifier = modifier.padding(8.dp)) {
            Text("Show Clicked")
        }
        Text(showSelected.value)
    }

}