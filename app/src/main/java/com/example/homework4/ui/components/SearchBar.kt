package com.example.homework4.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchValue by remember { mutableStateOf("") }

    TextField(
        value = searchValue,
        onValueChange = {searchValue = it},
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchValue)
            }
        ),
        placeholder = { Text("Search here...") },
        trailingIcon = {
            if (searchValue.isNotEmpty()) {
                IconButton(onClick = { searchValue = "" }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear text")
                }
            }
        }
    )
    IconButton(
        onClick = {
            onSearch(searchValue)
        }, modifier = Modifier
            .padding(10.dp)
            .height(40.dp)
    ) {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }
}