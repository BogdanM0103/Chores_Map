package com.bogdan.choresmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.choresmap.model.Chore
import com.bogdan.choresmap.model.ChoreRepository
import com.bogdan.choresmap.ui.theme.ChoresMapTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoresMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    ChoresListTest(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Chore(name: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = name,
                fontSize = 25.sp,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(
                onClick = { /* Handle delete action */ },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Chore",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(64.dp)
                )
            }
        }
    }
}

@Composable
fun ChoreList(chores: List<Chore>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
//            .fillMaxWidth()
    ) {
        items(chores) { chore ->
            Chore(
                name = chore.name,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ChoresListTest(modifier: Modifier) {
    var chores by remember {
        mutableStateOf(ChoreRepository.getChore())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, bottom = 32.dp) // Optional padding for screen edges
    ) {
        // List of chores at the top, takes up remaining space
        ChoreList(
            chores = chores,
            modifier = Modifier
                .padding(bottom = 80.dp) // Add padding to avoid overlap with button
        )

        // Button aligned to the bottom
        AddChoreButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Composable
fun AddChoreButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Chore",
            modifier = Modifier
                .padding(end = 4.dp)
        )
        Text(
            text = "Add Chore",
            fontSize = 25.sp
        )
    }
}

@Preview
@Composable
fun ChoresListTestPreview() {
    ChoresMapTheme {
        ChoresListTest(modifier = Modifier)
    }
}

@Preview
@Composable
fun AddChoreButtonPreview() {
    ChoresMapTheme {
        AddChoreButton(onClick = {})
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview
//@Composable
//fun GreetingPreview() {
//    ChoresMapTheme {
//        Greeting("Android")
//    }
//}