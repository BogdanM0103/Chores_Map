package com.bogdan.choresmap.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.choresmap.model.Chore
import com.bogdan.choresmap.model.ChoreRepository

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

@Preview
@Composable
fun ChoreListPreview() {
    ChoreList(
        chores = ChoreRepository.getChore(),
        modifier = Modifier
    )
}