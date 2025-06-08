package com.example.borutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.borutoapp.ui.theme.EXTRA_SMAIL_PADDING
import com.example.borutoapp.ui.theme.LightGray
import com.example.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMAIL_PADDING
) {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember { PathParser().parsePathString(stringStarPath).toPath() }
    val starBound = remember { starPath.getBounds() }

    val result = calculateStar(rating = rating)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filled"]?.let {
            repeat(it) {
                FilledStar(starPath = starPath, starBound = starBound, scaleFactor = scaleFactor)
            }
        }
        result["half"]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starBound = starBound,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["empty"]?.let {
            repeat(it) {
                EmptyStar(starPath = starPath, starBound = starBound, scaleFactor = scaleFactor)
            }
        }
    }
}

@Composable
fun calculateStar(rating: Double): Map<String, Int> {
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
        } else {
            Log.d("RAT", "calculateStar: invalid Number For Rating")
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
fun FilledStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)

        scale(scaleFactor) {
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
fun HalfFilledStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)

        scale(scaleFactor) {
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = .5f)
                )
                clipPath(starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starBound.maxDimension / 1.7f,
                            height = starBound.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        val starWidth = starBound.width
        val starHeight = starBound.height
        val left = (canvasSize.width / 2f) - (starWidth / 1.7f)
        val top = (canvasSize.height / 2f) - (starHeight / 1.7f)

        scale(scaleFactor) {
            translate(left = left, top = top) {
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
    val starPath = remember { PathParser().parsePathString(stringStarPath).toPath() }
    val starBound = remember { starPath.getBounds() }
    FilledStar(starPath = starPath, starBound = starBound, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
private fun HalfFilledStarPreview() {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember { PathParser().parsePathString(stringStarPath).toPath() }
    val starBound = remember { starPath.getBounds() }
    HalfFilledStar(starPath = starPath, starBound = starBound, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
private fun EmptyStarPreview() {
    val stringStarPath = stringResource(id = R.string.star_path)
    val starPath = remember { PathParser().parsePathString(stringStarPath).toPath() }
    val starBound = remember { starPath.getBounds() }
    EmptyStar(starPath = starPath, starBound = starBound, scaleFactor = 3f)
}