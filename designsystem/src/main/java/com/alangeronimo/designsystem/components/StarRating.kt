package com.alangeronimo.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alangeronimo.designsystem.ui.theme.Accent
import com.alangeronimo.designsystem.ui.theme.SakeTourTheme

const val MAX_RATING = 5

@Composable
fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = MAX_RATING,
) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            if (i <= rating) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Accent,
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Accent.copy(alpha = 0.3f),
                )
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewStarRating() {
    SakeTourTheme {
        StarRating(4.5f, Modifier, 5)
    }
}
