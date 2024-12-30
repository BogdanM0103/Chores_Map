package com.bogdan.choresmap.Frontend.Screens.HomeScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bogdan.choresmap.Backend.Chore

/*
    This is the List that contains all the Chores diven by the user.
    It starts empty.
 */

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