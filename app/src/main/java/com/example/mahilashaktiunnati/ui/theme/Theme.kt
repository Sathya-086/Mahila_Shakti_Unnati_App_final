package com.example.mahilashaktiunnati.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Light color scheme — primary colors for the app.
 * Soft Purple / Green / White / Light Orange palette.
 */
private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = TextOnPrimary,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = OnPurpleContainer,
    secondary = GreenSecondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = GreenContainer,
    onSecondaryContainer = OnGreenContainer,
    tertiary = OrangeAccent,
    tertiaryContainer = OrangeContainer,
    onTertiaryContainer = OnOrangeContainer,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed,
    onError = TextOnPrimary,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF991B1B)
)

/**
 * Dark color scheme for dark mode support.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = OnPurpleContainer,
    primaryContainer = PurpleDark,
    onPrimaryContainer = PurpleContainer,
    secondary = Green80,
    onSecondary = OnGreenContainer,
    secondaryContainer = GreenDark,
    onSecondaryContainer = GreenContainer,
    tertiary = OrangeAccent,
    tertiaryContainer = Color(0xFF4E3000),
    onTertiaryContainer = OrangeContainer,
    background = BackgroundDark,
    onBackground = Color(0xFFE0E0E0),
    surface = SurfaceDark,
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Color(0xFFB0B0B0),
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF1A1A1A),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFCA5A5)
)

/**
 * Main theme composable for Mahila-Shakti Unnati.
 * Supports both light and dark themes.
 */
@Composable
fun MahilaShaktiUnnatiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

// ── Import for Color constructor ─────────────────────────────
private fun Color(value: Long) = androidx.compose.ui.graphics.Color(value)
