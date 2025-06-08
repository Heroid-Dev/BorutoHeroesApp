package com.example.borutoapp.presentation.screens.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.components.RatingWidget2
import com.example.borutoapp.presentation.components.ShimmerEffect
import com.example.borutoapp.ui.theme.HERO_ITEM_HEIGHT
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.topAppBarContentColor
import com.example.borutoapp.util.Constant.BASE_URL


@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavController,
    paddingValues:PaddingValues= PaddingValues(all = 0.dp)
) {
    val handle = handlePagingResult(heroes = heroes,paddingValues)
    if(handle){
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(count = heroes.itemCount,
                key = heroes.itemKey { hero ->
                    hero.id
                }) {
                heroes[it]?.let { hero -> HeroItem(hero = hero, navController = navController) }
            }
        }
    }

}

@Composable
fun handlePagingResult(heroes: LazyPagingItems<Hero>,paddingValues: PaddingValues):Boolean {
    heroes.apply {
        val error=when{
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when{
            error!=null->{
                EmptyScreen(error = error,heroes=heroes)
                false
            }
            loadState.refresh is LoadState.Loading ->{
                ShimmerEffect(paddingValues)
                false
            }
            heroes.itemCount<1 ->{
                EmptyScreen()
                false
            }

            else-> true
        }
    }
}

@Composable
fun HeroItem(
    hero: Hero,
    navController: NavController,
) {
    val paint = rememberAsyncImagePainter(
        model = "$BASE_URL${hero.image}",
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder)
    )
    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.DetailScreen.name + "/${hero.id}")
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = ShapeDefaults.Large) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = paint,
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = .5f),
            shape = RoundedCornerShape(bottomEnd = MEDIUM_PADDING, bottomStart = MEDIUM_PADDING)
        ) {
            Column(modifier = Modifier.padding(MEDIUM_PADDING)) {
                Text(
                    text = hero.name,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = Color.White.copy(alpha = .5f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget2(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(.5f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HeroItem2Preview() {
    HeroItem(
        hero =
        Hero(
            id = 1,
            name = "Naruto",
            image = "",
            about = "Naruto is Amazing Hero",
            rating = 4.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HeroItem2DarkPreview() {
    HeroItem(
        hero =
        Hero(
            id = 1,
            name = "Naruto",
            image = "",
            about = "Naruto is Amazing Hero",
            rating = 4.0,
            power = 0,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}