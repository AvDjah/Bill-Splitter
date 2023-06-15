package com.example.billsplitter.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.billsplitter.Screens
import com.example.billsplitter.models.Expense
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel,
    navController: NavHostController,
    setExpenseItem: (id: Int, amount : Float, expenseShare : MutableMap<Int,Float>) -> Unit
) {

    val expenseList by startScreenViewModel.expenseList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("History")
            })
        }
    ) {
        StartScreenMain(
            paddingValues = it,
            expenseList = expenseList,
            onItemClick = { id, amount, expenseShare ->
                setExpenseItem(id,amount,expenseShare)
                navController.navigate(Screens.ADD_EXPENSE_SCREEN.name)
            }, onAddExpense = {
                navController.navigate(Screens.SELECT_USER_SCREEN.name)
            })
    }
}

@Composable
fun StartScreenMain(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    expenseList: List<Expense>,
    onItemClick: (id: Int, amount: Float, expenseShare: MutableMap<Int, Float>) -> Unit,
    onAddExpense : ()->Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {
        item {
            Button(onClick = {
                onAddExpense()
            }, modifier = modifier.padding(8.dp)) {
                Text("Add Expense")
            }
        }
        items(expenseList) { item ->
            val amount = item.amount
            val share = item.expenseShares
            val shareMap = Json.decodeFromString<MutableMap<Int, Float>>(share)
            Card(modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    onItemClick(item.id, item.amount, shareMap)
                }, elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Row(
                    modifier = modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = amount.toString())
                    Text(text = shareMap.toList().toString())
                }
            }

        }
    }
}