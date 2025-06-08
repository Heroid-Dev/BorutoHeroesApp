package com.example.borutoapp.presentation.screens.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.ui.theme.DarkGray
import com.example.borutoapp.ui.theme.LightGray
import com.example.borutoapp.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.example.borutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<Hero>? = null,
) {
    var message by remember {
        mutableStateOf("Find Your Favorite Hero!")
    }
    var icon by remember {
        mutableStateOf(R.drawable.search_document)
    }

    if (error != null) {
        message = parseErrorMessage(error)
        icon = R.drawable.network_error
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 0.38f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyColumn(alphaAnim, icon, message, error, heroes)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EmptyColumn(
    alphaAnim: Float,
    icon: Int,
    message: String,
    error: LoadState.Error?,
    heroes: LazyPagingItems<Hero>?,
) {

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefresh = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            heroes?.refresh()
            isRefreshing = false
        })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(
                state = pullRefresh,
                enabled = error != null
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_HEIGHT)
                    .alpha(alphaAnim),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.network_image),
                tint = if (isSystemInDarkTheme()) LightGray else DarkGray
            )
            Text(
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .alpha(alphaAnim),
                text = message,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
        PullRefreshIndicator(
            refreshing = isRefreshing, state = pullRefresh,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Connection."
        }

        is ConnectException -> {
            "Internet Connection."
        }

        else -> "Unknown Error."
    }
}


