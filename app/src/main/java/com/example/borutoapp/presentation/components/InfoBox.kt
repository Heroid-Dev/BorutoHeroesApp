package com.example.borutoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.INFO_ICON_SIZE
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.titleColor

@Composable
fun InfoBox(
    icon: Painter,
    iconColor: Color,
    textLarge: String,
    textSmall: String,
    textColor: Color,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .padding(end = SMALL_PADDING)
                .size(INFO_ICON_SIZE),
            painter = icon,
            contentDescription = stringResource(id = R.string.info_icon),
            tint = iconColor
        )
        Column {
            Text(
                text = textLarge,
                color = textColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Black
            )
            Text(
                text = textSmall,
                color = textColor,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoBoxPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.bolt),
        iconColor = MaterialTheme.colorScheme.primary,
        textLarge = "96",
        textSmall = "about this",
        textColor = MaterialTheme.colorScheme.titleColor
    )
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InfoBoxDarkPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.bolt),
        iconColor = MaterialTheme.colorScheme.primary,
        textLarge = "96",
        textSmall = "about this",
        textColor = MaterialTheme.colorScheme.titleColor
    )
}
