package com.example.billsplitter

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.billsplitter.Screen.AddExpenseScreen
import com.example.billsplitter.Screen.AddExpenseScreenViewModel
import com.example.billsplitter.Screen.SelectUserScreen
import com.example.billsplitter.Screen.SelectUserScreenViewModel
import com.example.billsplitter.Screen.StartScreen
import com.example.billsplitter.models.Friend
import kotlinx.coroutines.launch


enum class Screens {
    START_SCREEN, SELECT_USER_SCREEN, ADD_EXPENSE_SCREEN
}

enum class Dialogs {
    ADD_FRIEND
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val selectUserScreenViewModel: SelectUserScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val addExpenseScreenViewModel: AddExpenseScreenViewModel = viewModel()
    val frenList = selectUserScreenViewModel.friendsList.collectAsState()
    val expenseList = addExpenseScreenViewModel.expenseFriends.collectAsState()


    NavHost(navController = navController, startDestination = Screens.SELECT_USER_SCREEN.name) {
        composable(route = Screens.START_SCREEN.name) {
            StartScreen()
        }
        composable(route = Screens.SELECT_USER_SCREEN.name) {
            SelectUserScreen(navController = navController,
                selectUserScreenViewModel = selectUserScreenViewModel,
                selectFinalExpenseFriends = { list, set ->
                    addExpenseScreenViewModel.updateFriendList(list, set)
                    Log.d("FromNav for AddItems", expenseList.value.toList().toString())
                })
        }
        composable(route = Screens.ADD_EXPENSE_SCREEN.name) {
            AddExpenseScreen(
                navController = navController, addExpenseScreenViewModel = addExpenseScreenViewModel
            )
        }
        dialog(route = Dialogs.ADD_FRIEND.name) {
            var name = remember {
                mutableStateOf("")
            }
            Dialog(
                onDismissRequest = {
                    navController.popBackStack(Screens.SELECT_USER_SCREEN.name, false)
                }, properties = DialogProperties(
                    dismissOnBackPress = true
                )
            ) {
                val coroutineScope = rememberCoroutineScope()
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.DarkGray)
                ) {
                    TextField(value = name.value, onValueChange = { value ->
                        name.value = value
                    }, modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                    Button(modifier = modifier.padding(8.dp), onClick = {
                          coroutineScope.launch {
                              selectUserScreenViewModel.saveFriend(Friend(
                                  name.value,0
                              ))
                          }
                        selectUserScreenViewModel.addUser(name.value)
//                        Log.d("FRENS", frenList.value.toList().toString())
                        navController.popBackStack(Screens.SELECT_USER_SCREEN.name, false)
                    }) {
                        Text("Add User", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }

}