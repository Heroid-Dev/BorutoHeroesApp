package com.example.borutoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.LightGray
import com.example.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget2(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = 6.dp,
) {

    val result = CalculateStar(rating = rating)

    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(stringStarPath).toPath()
    }
    val starPathBound = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        result["filled"]?.let {
            repeat(it) {
                FilledStar(starPath = starPath, scale = scaleFactor, starBound = starPathBound)
            }
        }
        result["half"]?.let {
            repeat(it) {
                HalfFilledStar(starPath = starPath, scale = scaleFactor, starBound = starPathBound)
            }
        }
        result["empty"]?.let {
            repeat(it) {
                EmptyStar(starPath = starPath, scale = scaleFactor, starBound = starPathBound)
            }
        }
    }
}

@Composable
fun CalculateStar(rating: Double): Map<String, Int> {
    val maxStar by remember { mutableIntStateOf(5) }
    var filledStar by remember { mutableIntStateOf(0) }
    var halfStar by remember { mutableIntStateOf(0) }
    var emptyStar by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString().split(".").map { it.toInt() }
        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStar = firstNumber
            if (lastNumber in 1..5) {
                halfStar++
            }
            if (lastNumber in 6..9) {
                filledStar++
            }
            if (firstNumber == 5 && lastNumber in 1..9) {
                filledStar = 0
                halfStar = 0
                emptyStar = maxStar
            }
        }
    }
    emptyStar = maxStar - (filledStar + halfStar)
    return mapOf(
        "filled" to filledStar,
        "half" to halfStar,
        "empty" to emptyStar
    )
}

@Composable
fun FilledStar(starPath: Path, scale: Float, starBound: Rect) {
    Canvas(modifier = Modifier.size(24.dp)) {
        var canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)

        scale(scale = scale) {
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = StarColor
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(starPath: Path, scale: Float, starBound: Rect) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)
        scale(scale) {
            translate(top = top, left = left) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = .5f)
                )
                clipPath(starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starBound.maxDimension / 1.7f,
                            height = starBound.maxDimension * scale
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(starPath: Path, scale: Float, starBound: Rect) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)
        scale(scale) {
            translate(top = top, left = left) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = .5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledStarPreview() {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(stringStarPath).toPath()
    }
    val starPathBound = remember {
        starPath.getBounds()
    }
    FilledStar(starPath = starPath, starBound = starPathBound, scaleFactor = 2f)
}

@Preview(showBackground = true)
@Composable
private fun HalfFilledStarPreview() {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(stringStarPath).toPath()
    }
    val starPathBound = remember {
        starPath.getBounds()
    }
    HalfFilledStar(starPath = starPath, starBound = starPathBound, scaleFactor = 2f)
}

@Preview(showBackground = true)
@Composable
private fun EmptyStarPreview() {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(stringStarPath).toPath()
    }
    val starPathBound = remember {
        starPath.getBounds()
    }
    EmptyStar(starPath = starPath, starBound = starPathBound, scaleFactor = 2f)
}