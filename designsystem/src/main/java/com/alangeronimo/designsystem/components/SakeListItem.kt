package com.alangeronimo.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alangeronimo.designsystem.ui.theme.Dimens
import com.alangeronimo.designsystem.ui.theme.SakeTourTheme
import com.alangeronimo.designsystem.ui.theme.TextSecondary
import com.alangeronimo.domain.model.SakeShop

@SuppressLint("DefaultLocale")
@Composable
fun SakeListItem(
    shop: SakeShop,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingMedium, vertical = Dimens.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = shop.name, style = MaterialTheme.typography.titleSmall)
                Text(text = shop.address, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            StarRating(shop.rating, Modifier.padding(PaddingValues(0.dp, 0.dp, Dimens.PaddingSmall, 0.dp)), 5)
            Text(text = String.format("%.1f", shop.rating), style = MaterialTheme.typography.titleMedium)
        }
        HorizontalDivider(thickness = Dimens.DividerHeight)
    }
}

@Preview(showBackground = true)
@Composable
internal fun SakeListItemPreview() {
    val sampleShop =
        SakeShop(
            name = "The Sake House",
            description = "Fine Japanese sake",
            picture = null,
            rating = 4.2f,
            address = "123 Main St, Any town",
            googleMapsLink = "",
            website = "",
        )

    SakeTourTheme {
        SakeListItem(
            shop = sampleShop,
            onClick = {},
        )
    }
}
