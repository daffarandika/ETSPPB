package com.itsrobocon.ets_ppb.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = WarmAmberDarkCTA,
    onPrimary = DarkForestBg,
    primaryContainer = WarmAmberDarkCTA.copy(alpha = 0.25f),
    onPrimaryContainer = WarmAmberDarkCTA,
    inversePrimary = DeepAmber,

    secondary = MutedSageText,
    onSecondary = DarkForestBg,
    secondaryContainer = DarkMossCard,
    onSecondaryContainer = SoftSageText,

    tertiary = MutedSageText,
    onTertiary = DarkForestBg,
    tertiaryContainer = DarkMossCard,
    onTertiaryContainer = SoftSageText,

    background = DarkForestBg,
    onBackground = SoftSageText,

    surface = DarkMossCard,
    onSurface = SoftSageText,
    surfaceVariant = DarkMossCard,
    onSurfaceVariant = MutedSageText,
    surfaceTint = WarmAmberDarkCTA,
    inverseSurface = SoftSageText,
    inverseOnSurface = DarkForestBg,

    surfaceDim = SurfaceDimDark,
    surfaceBright = SurfaceBrightDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,

    outline = MutedSageText.copy(alpha = 0.2f),
    outlineVariant = MutedSageText.copy(alpha = 0.15f)
)

private val LightColorScheme = lightColorScheme(
    primary = HoneyGoldCTA,
    onPrimary = DeepSlateForest,
    primaryContainer = HoneyGoldCTA.copy(alpha = 0.35f),
    onPrimaryContainer = DeepSlateForest,
    inversePrimary = WarmAmberDarkCTA,

    secondary = EarthySlate,
    onSecondary = SoftSageBg,
    secondaryContainer = MutedSageCard,
    onSecondaryContainer = DeepSlateForest,

    tertiary = EarthySlate,
    onTertiary = SoftSageBg,
    tertiaryContainer = MutedSageCard,
    onTertiaryContainer = DeepSlateForest,

    background = SoftSageBg,
    onBackground = DeepSlateForest,

    surface = MutedSageCard,
    onSurface = DeepSlateForest,
    surfaceVariant = MutedSageCard,
    onSurfaceVariant = EarthySlate,
    surfaceTint = HoneyGoldCTA,
    inverseSurface = DeepSlateForest,
    inverseOnSurface = SoftSageBg,

    surfaceDim = SurfaceDimLight,
    surfaceBright = SurfaceBrightLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,

    outline = EarthySlate.copy(alpha = 0.3f),
    outlineVariant = EarthySlate.copy(alpha = 0.15f)
)

@Composable
fun ETSPPBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set default dynamicColor to false to use custom Theme.kt colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
