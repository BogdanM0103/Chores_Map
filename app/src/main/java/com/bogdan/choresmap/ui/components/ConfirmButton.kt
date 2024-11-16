package com.bogdan.choresmap.ui.components

import android.R.attr.onClick
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmButton(
    text: String = "Confirm",
    onClick: () -> Unit = {},
    modifier: Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row() {
            Icon(
               imageVector = Icons.Filled.Check,
                contentDescription = "Confirm",
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = text,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview() {
    ConfirmButton(modifier = Modifier)
}