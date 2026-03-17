package com.salmandev.imusic.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SpotifyGreen,
    onPrimary = SpotifyBlack,
    primaryContainer = SpotifyGreenDark,
    onPrimaryContainer = SpotifyWhite,
    secondary = AccentPurple,
    onSecondary = SpotifyWhite,
    secondaryContainer = Color(0xFF3D2555),
    onSecondaryContainer = SpotifyWhite,
    tertiary = AccentBlue,
    onTertiary = SpotifyWhite,
    background = DarkBackground,
    onBackground = SpotifyWhite,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = Color(0xFF404040),
    outlineVariant = Color(0xFF2A2A2A),
    error = ErrorRed,
    onError = SpotifyWhite
)

private val LightColorScheme = lightColorScheme(
    primary = SpotifyGreen,
    onPrimary = SpotifyWhite,
    primaryContainer = Color(0xFFB8F5CE),
    onPrimaryContainer = Color(0xFF002114),
    secondary = AccentPurple,
    onSecondary = SpotifyWhite,
    secondaryContainer = Color(0xFFEDD9FF),
    onSecondaryContainer = Color(0xFF2C0054),
    tertiary = AccentBlue,
    onTertiary = SpotifyWhite,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = Color(0xFFCCCCCC),
    error = ErrorRed,
    onError = SpotifyWhite
)

@Composable
fun IMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = IMusicTypography,
        content = content
    )
}
