package pl.lemanski.pandaloop.presentation.visual.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.compose.backgroundDark
import com.example.compose.backgroundLight
import com.example.compose.errorContainerDark
import com.example.compose.errorContainerLight
import com.example.compose.errorDark
import com.example.compose.errorLight
import com.example.compose.inverseOnSurfaceDark
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.inversePrimaryDark
import com.example.compose.inversePrimaryLight
import com.example.compose.inverseSurfaceDark
import com.example.compose.inverseSurfaceLight
import com.example.compose.onBackgroundDark
import com.example.compose.onBackgroundLight
import com.example.compose.onErrorContainerDark
import com.example.compose.onErrorContainerLight
import com.example.compose.onErrorDark
import com.example.compose.onErrorLight
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryDark
import com.example.compose.onPrimaryLight
import com.example.compose.onSecondaryContainerDark
import com.example.compose.onSecondaryContainerLight
import com.example.compose.onSecondaryDark
import com.example.compose.onSecondaryLight
import com.example.compose.onSurfaceDark
import com.example.compose.onSurfaceLight
import com.example.compose.onSurfaceVariantDark
import com.example.compose.onSurfaceVariantLight
import com.example.compose.onTertiaryContainerDark
import com.example.compose.onTertiaryContainerLight
import com.example.compose.onTertiaryDark
import com.example.compose.onTertiaryLight
import com.example.compose.outlineDark
import com.example.compose.outlineLight
import com.example.compose.outlineVariantDark
import com.example.compose.outlineVariantLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.example.compose.primaryDark
import com.example.compose.primaryLight
import com.example.compose.scrimDark
import com.example.compose.scrimLight
import com.example.compose.secondaryContainerDark
import com.example.compose.secondaryContainerLight
import com.example.compose.secondaryDark
import com.example.compose.secondaryLight
import com.example.compose.surfaceBrightDark
import com.example.compose.surfaceBrightLight
import com.example.compose.surfaceContainerDark
import com.example.compose.surfaceContainerHighDark
import com.example.compose.surfaceContainerHighLight
import com.example.compose.surfaceContainerHighestDark
import com.example.compose.surfaceContainerHighestLight
import com.example.compose.surfaceContainerLight
import com.example.compose.surfaceContainerLowDark
import com.example.compose.surfaceContainerLowLight
import com.example.compose.surfaceContainerLowestDark
import com.example.compose.surfaceContainerLowestLight
import com.example.compose.surfaceDark
import com.example.compose.surfaceDimDark
import com.example.compose.surfaceDimLight
import com.example.compose.surfaceLight
import com.example.compose.surfaceVariantDark
import com.example.compose.surfaceVariantLight
import com.example.compose.tertiaryContainerDark
import com.example.compose.tertiaryContainerLight
import com.example.compose.tertiaryDark
import com.example.compose.tertiaryLight
import com.example.ui.theme.PandaTypography

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun PandaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else      -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = colorScheme.background.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PandaTypography,
        content = content
    )
}

