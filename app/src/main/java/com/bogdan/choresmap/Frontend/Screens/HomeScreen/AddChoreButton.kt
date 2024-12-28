package com.bogdan.choresmap.Frontend.Screens.HomeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.choresmap.Frontend.theme.ChoresMapTheme

/*
    This Button takes the user to AddChoreScreen
 */

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
fun AddChoreButtonPreview() {
    ChoresMapTheme {
        AddChoreButton(onClick = {})
    }
}