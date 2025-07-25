package com.example.borutoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.borutoapp.ui.theme.ABOUT_PLACEHOLDER_HEIGHT
import com.example.borutoapp.ui.theme.EXTRA_SMAIL_PADDING
import com.example.borutoapp.ui.theme.HERO_ITEM_HEIGHT
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.example.borutoapp.ui.theme.RATING_PLACEHOLDER_HEIGHT
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.ShimmerDarkGray
import com.example.borutoapp.ui.theme.ShimmerLightGray
import com.example.borutoapp.ui.theme.ShimmerMediumGray

@Composable
fun ShimmerEffect(paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(2) {
            AnimatedShimmerEffect()
        }
    }
}

@Composable
fun AnimatedShimmerEffect(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnimate by transition.animateFloat(
        initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    ShimmerItem(alpha = alphaAnimate)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(HERO_ITEM_HEIGHT)
            .padding(SMALL_PADDING),
        color = if (isSystemInDarkTheme()) Color.Black else ShimmerLightGray,
        shape = RoundedCornerShape(LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(NAME_PLACEHOLDER_HEIGHT)
                    .alpha(alpha = alpha),
                color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(LARGE_PADDING)
            ) {}
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ABOUT_PLACEHOLDER_HEIGHT)
                        .alpha(alpha = alpha),
                    color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(LARGE_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(EXTRA_SMAIL_PADDING))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .size(RATING_PLACEHOLDER_HEIGHT)
                            .alpha(alpha = alpha),
                        color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                        shape = RoundedCornerShape(EXTRA_SMAIL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMAIL_PADDING))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShimmerItemPreview() {
    AnimatedShimmerEffect()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShimmerItemDarkPreview() {
    AnimatedShimmerEffect()
}