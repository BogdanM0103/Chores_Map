package com.bogdan.choresmap.Frontend.Screens.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
    This is the composable that fill appear in the Chore List
    as a list item with the name and the delete icon.
 */

@Composable
fun Chore(
    name: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                onClick = onDeleteClick,
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

@Preview
@Composable
fun ChorePreview() {
    Chore(
        name = "Chore Preview",
        onDeleteClick = {},
        modifier = Modifier
    )
}