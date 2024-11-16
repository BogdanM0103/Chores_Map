package com.bogdan.choresmap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.choresmap.ui.components.ConfirmButton

@Composable
fun AddChoreScreen(onSubmit: (String, String) -> Unit = {_, _ -> }, modifier: Modifier) {
    var choreName by remember { mutableStateOf("") } // Now works correctly
    var choreDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input Field
        TextField(
            value = choreName,
            onValueChange = { choreName = it },
            label = { Text("Chore Name") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Description Field
        TextField(
            value = choreDescription,
            onValueChange = { /* Handle description change */ },
            label = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            fontSize = if (choreDescription.length > 30) 12.sp else 16.sp
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        // Confirm Button
        ConfirmButton(
            onClick = {
                if (choreName.isNotBlank() && choreDescription.isNotBlank()) {
                    onSubmit(choreName, choreDescription)
                }
            },
            modifier = Modifier
//                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun AddChoreScreenPreview() {
    AddChoreScreen(modifier = Modifier)
}
