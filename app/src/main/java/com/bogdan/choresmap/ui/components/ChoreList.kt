package com.bogdan.choresmap.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.choresmap.model.Chore

@Composable
fun ChoreList(
    chores: List<Chore>,
    onDeleteChore: (Chore) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
//            .fillMaxWidth()
    ) {
        items(chores) { chore ->
            Chore(
                name = chore.name,
                onDeleteClick = { onDeleteChore(chore) },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}