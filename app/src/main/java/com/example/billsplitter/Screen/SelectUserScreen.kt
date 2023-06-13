package com.example.billsplitter.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billsplitter.Dialogs

@Composable
fun SelectUserScreen(modifier : Modifier = Modifier, navController: NavController
,selectUserScreenViewModel: SelectUserScreenViewModel) {
//    val selectUserScreenViewModel: SelectUserScreenViewModel = viewModel()
    val friendsList = selectUserScreenViewModel.friendsList.collectAsState()



    Column(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        Text("Select Friends")
        Button(onClick = {
                        navController.navigate(Dialogs.ADD_FRIEND.name)
        }, modifier = modifier.padding(8.dp)) {
            Text(text = "Add Friend", textAlign = TextAlign.Center)
        }
        LazyColumn {
            items(friendsList.value) { item ->
                Row(modifier = modifier.padding(8.dp)) {
                    Text(item.name)
                }
            }
        }
    }

}