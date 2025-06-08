package com.example.borutoapp.presentation.screens.detail

import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.presentation.components.InfoBox
import com.example.borutoapp.presentation.components.OrderedList
import com.example.borutoapp.ui.theme.EXTRA_LARGE_PADDING
import com.example.borutoapp.ui.theme.INFO_ICON_SIZE
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_ALPHA_VALUE
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.MIN_SHEET_HEIGHT
import com.example.borutoapp.ui.theme.titleColor
import com.example.borutoapp.util.Constant.ABOUT_TEXT_MAX_LINES
import com.example.borutoapp.util.Constant.BASE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navController: NavController,
    selectedHero: Hero?,
    colors: Map<String, String>
) {


    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#ffffff") }

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }

//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(
//        color = Color(parseColor(darkVibrant))
//    )




    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        )

    )

    val currentSheetFraction = scaffoldState.currentFraction

    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f) {
            EXTRA_LARGE_PADDING
        } else 0.dp
    )

    BottomSheetScaffold(
        sheetContainerColor = Color(parseColor(darkVibrant)),
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let { BottomSheetContent(selectedHero = it,
                infoBoxIconColor = Color(parseColor(vibrant)),
                sheetColor = Color(parseColor(darkVibrant)),
                contentColor = Color(parseColor(onDarkVibrant))) }
        }
    ) {
        selectedHero?.let { hero ->
            BackgroundContent(
                heroImage = hero.image, paddingValue = it,
                imageFraction = currentSheetFraction,
                backgroundColor = Color(parseColor(darkVibrant))
            ) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun BackgroundContent(
    heroImage: String,
    paddingValue: PaddingValues,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onCloseClicked: () -> Unit,
) {
    val imageUrl = "$BASE_URL$heroImage"
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(id = R.drawable.placeholder),
        placeholder = painterResource(id = R.drawable.placeholder)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue)
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(MEDIUM_PADDING),
                onClick = onCloseClicked
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        val fraction by derivedStateOf {
            runCatching {
                bottomSheetState.requireOffset()
            }.getOrDefault(0f) / BottomSheetDefaults.SheetPeekHeight.value * 0.01f
        }

        val currentValue = bottomSheetState.currentValue
        val targetValue = bottomSheetState.targetValue

        Log.d("Fraction", "Fraction: $fraction")
        Log.d("Fraction", "TargetValue: $currentValue")
        Log.d("Fraction", "CurrentValue: $targetValue")
        return when {
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 0.4f
            currentValue == SheetValue.PartiallyExpanded && targetValue == SheetValue.PartiallyExpanded -> 1f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.PartiallyExpanded -> 0.6f + fraction
            currentValue == SheetValue.PartiallyExpanded && targetValue == SheetValue.Expanded -> 0.19f + fraction
            else -> fraction
        }
    }

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colorScheme.primary,
    sheetColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.titleColor,
) {
    Column(
        modifier = Modifier
            .background(sheetColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_App),
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(8f),
                text = selectedHero.name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.bolt),
                iconColor = infoBoxIconColor,
                textLarge = selectedHero.power.toString(),
                textSmall = stringResource(R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.calendar),
                iconColor = infoBoxIconColor,
                textLarge = selectedHero.month,
                textSmall = stringResource(R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.cake),
                iconColor = infoBoxIconColor,
                textLarge = selectedHero.day,
                textSmall = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }


        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
        Text(
            modifier = Modifier
                .alpha(MEDIUM_ALPHA_VALUE)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES,
            overflow = TextOverflow.Ellipsis
        )


        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 4,
            name = "Boruto",
            image = "/images/boruto.png",
            about = "Boruto Uzumaki (うずまきボルト, Uzumaki Boruto) is a shinobi from Konohagakure's Uzumaki clan and a direct descendant of the Hyūga clan through his mother. While initially resentful of his father and his absence since becoming Hokage, Boruto eventually comes to respect his father and duties.",
            rating = 4.9,
            power = 95,
            month = "Mar",
            day = "27th",
            family = listOf(
                "Naruto",
                "Hinata",
                "Hanabi",
                "Himawari",
                "Kawaki"
            ),
            abilities = listOf(
                "Karma",
                "Jogan",
                "Rasengan",
                "Intelligence"
            ),
            natureTypes = listOf(
                "Lightning",
                "Wind",
                "Water"
            )
        )
    )
}
