package com.alangeronimo.designsystem.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme =
    darkColorScheme(
        primary = PrimaryColor,
        secondary = SecondaryColor,
        background = BackgroundColor,
        onBackground = TextPrimary,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PrimaryColor,
        secondary = SecondaryColor,
        background = BackgroundColor,
        onBackground = TextPrimary,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
    )

@Composable
fun SakeTourTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    SideEffect {
        systemUiController.setStatusBarColor(
            color = PrimaryColor,
            darkIcons = useDarkIcons,
        )
        systemUiController.setNavigationBarColor(
            color = BackgroundColor,
            darkIcons = true,
        )
    }

    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}
