package com.example.borutoapp.presentation.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.topAppBarBackgroundColor
import com.example.borutoapp.ui.theme.topAppBarContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    text: String,
    onValueChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    SearchWidget(
        text,
        onValueChanged,
        onCloseClicked,
        onSearchClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchWidget(
    text: String,
    onValueChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val localKeyboard= LocalSoftwareKeyboardController.current
    TopAppBar(
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onValueChanged,
                placeholder = {
                    Text(
                        text = "Search here...",
                        color = Color.White.copy(alpha = .5f)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (text.isNotEmpty()) {
                            onValueChanged("")
                        } else {
                            onCloseClicked()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_icon),
                            tint = MaterialTheme.colorScheme.topAppBarContentColor
                        )
                    }
                },
                leadingIcon = {
                    IconButton(modifier = Modifier.alpha(alpha = .5f),
                        onClick = {
                            onSearchClicked(text)
                        }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon2),
                            tint = MaterialTheme.colorScheme.topAppBarContentColor
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.topAppBarContentColor,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.topAppBarContentColor,
                    focusedTextColor = MaterialTheme.colorScheme.topAppBarContentColor,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                        localKeyboard?.hide()
                    }
                ),
                shape = RectangleShape,
                maxLines = 1,
                )

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchTopBar(
        text = "",
        onValueChanged = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}