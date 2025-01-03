package com.bogdan.choresmap.Frontend.Screens.MapScreen

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
    This button takes the user to the home screen of the app.
 */

@Composable
fun HomeButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Map,
            contentDescription = "Home",
            modifier = Modifier
                .size(42.dp)
        )
    }
}

@Preview
@Composable
fun HomeButtonPreview() {
    HomeButton(onClick = {}, modifier = Modifier)
}