package com.example.homework4.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.homework4.files.Filter

@Composable
fun FilterButton(
    filterList: List<Filter>,
    onFilterSelected: (Filter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            expanded = true
        }, modifier = Modifier
            .padding(2.dp)
            .height(40.dp)
    ) {
        Icon(Icons.Outlined.List, contentDescription = "List")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }) {
        filterList.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                onFilterSelected(item)
                expanded = false

            }) {
                Text(item.name)
            }
        }
    }
}