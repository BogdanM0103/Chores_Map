package com.bogdan.choresmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.choresmap.model.Chore
import com.bogdan.choresmap.ui.theme.ChoresMapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoresMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
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
            .height(50.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}

@Composable
fun ChoreList(chores: List<Chore>) {
    Column() {
        LazyColumn() {
            items(chores.size) { index ->
                Chore(
                    name = chores[index].getName,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 2.dp
                        )
                )
            }
        }
    }
}

@Composable
fun ChoresListTest() {
    val chores = listOf(
        Chore(
            id = 1,
            name = "Chore 1"
        ),
        Chore(
            id = 2,
            name = "Chore 2"
        ),
        Chore(
            id = 3,
            name = "Chore 3"
        )
    )
    Column(
        modifier = Modifier
    ) {
        ChoreList(chores = chores)
        Spacer(modifier = Modifier.height(50.dp))
        AddChoreScreen(onClick = {})
    }
}

@Preview
@Composable
fun ChoresListTestPreview() {
    ChoresListTest()
}

@Composable
fun AddChoreScreen(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Add Chore")
    }
}

@Preview
@Composable
fun AddChoreScreenPreview() {
    AddChoreScreen(onClick = {})
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview
@Composable
fun GreetingPreview() {
    ChoresMapTheme {
        Greeting("Android")
    }
}

