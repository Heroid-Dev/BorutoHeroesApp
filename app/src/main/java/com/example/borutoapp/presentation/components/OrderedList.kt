package com.example.borutoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.borutoapp.ui.theme.MEDIUM_ALPHA_VALUE
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.titleColor

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: Color,
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.alpha(alpha = MEDIUM_ALPHA_VALUE),
                text = "${index+1}. $item",
                color = textColor,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun OrderedListPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Shadow", "Sundae", "Jimin"),
        textColor = MaterialTheme.colorScheme.titleColor
    )
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OrderedLisDarkPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Shadow", "Sundae", "Jimin"),
        textColor = MaterialTheme.colorScheme.titleColor
    )
}
