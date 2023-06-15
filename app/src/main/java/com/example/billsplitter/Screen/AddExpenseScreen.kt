package com.example.billsplitter.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.billsplitter.Screens
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
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

    var finishedSplit = remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val localContext = LocalContext.current

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
            Text("Description:")
            TextField(
                value = description, onValueChange = {
                    description = it
                }, modifier = modifier
                    .padding(8.dp)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
        }
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
            val trial = navController.popBackStack()
            if (!trial) {
                navController.navigate(Screens.SELECT_USER_SCREEN.name) {
                    popUpTo(Screens.ADD_EXPENSE_SCREEN.name) {
                        inclusive = true
                    }
                }
            }
        }, modifier = modifier.padding(8.dp)) {
            Text("Go Back")
        }
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(300.dp)
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
                        addExpenseScreenViewModel.updateChecklist(it, item.frenId)
                    })
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                if (amount.value.isEmpty()) {
                    amount.value = "0"
                }
                addExpenseScreenViewModel.splitAExpense(amount.value.toFloat())
                Toast.makeText(localContext, "Added to split!", Toast.LENGTH_SHORT).show()
            }, modifier = modifier.padding(8.dp)) {
                Text("Split this")
            }
            Button(onClick = {
                if (amount.value.isEmpty()) {
                    amount.value = "0"
                }
                val finishedResult = addExpenseScreenViewModel.getFinishedSplit()
                addExpenseScreenViewModel.addFinishedMap(description.ifEmpty { "Kharcha" })
//                val currentChecklist = addExpenseScreenViewModel.getChecklist()
                finishedSplit.value = finishedResult
                Log.d("FINISHED SPLIT", finishedResult)
            }, modifier = modifier.padding(8.dp)) {
                Text("Finish Splitting")
            }
        }
        val gradientColors = listOf(Cyan, Color.Blue)
        TextField(
            value = finishedSplit.value,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
            textStyle = TextStyle(
                brush = Brush.linearGradient(colors = gradientColors),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            ), readOnly = true
        )
//        Text(text = finishedSplit.value)

    }
}