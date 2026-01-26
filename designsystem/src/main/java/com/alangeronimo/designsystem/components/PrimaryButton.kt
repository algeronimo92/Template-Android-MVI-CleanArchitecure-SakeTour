package com.alangeronimo.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alangeronimo.designsystem.ui.theme.Accent
import com.alangeronimo.designsystem.ui.theme.Dimens
import com.alangeronimo.designsystem.ui.theme.SakeTourTheme

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Accent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = Dimens.PaddingMedium, vertical = 0.dp),
        )
    }
}

@Preview
@Composable
internal fun PreviewPrimaryTextButton() {
    SakeTourTheme {
        PrimaryTextButton(text = "Primary Text Button", onClick = {}, modifier = Modifier)
    }
}
