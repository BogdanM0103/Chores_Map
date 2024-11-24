package com.bogdan.choresmap.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MapButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { /* Handle button click */ },
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Map,
            contentDescription = "Map",
            modifier = Modifier
                .size(42.dp)
        )
    }
}
@Preview
@Composable
fun MapButtonPreview() {
    MapButton()
}