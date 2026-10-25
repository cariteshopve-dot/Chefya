package agency.lacosta.chefya.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import agency.lacosta.chefya.data.ThemeMode

/** Colores propios de ChefYa que Material no tiene: la cabecera mango, las fichas y las tarjetas de receta. */
@Immutable
data class MangoColors(
    val hero: Color,
    val onHero: Color,
    val chip: Color,
    val chipEdge: Color,
    val dash: Color,
    val card: Color,
    val onCard: Color,
    val pill: Color,
    val onPill: Color,
    val missing: Color,
)

private val LightMango = MangoColors(
    hero = Mango,
    onHero = PetrolDeep,
    chip = White,
    chipEdge = CreamEdge,
    dash = Dash,
    card = Petrol,
    onCard = Cream,
    pill = Mango,
    onPill = PetrolDeep,
    missing = Tomato,
)

private val DarkMango = MangoColors(
    hero = Mango,
    onHero = PetrolDeep,
    chip = LagoonCard,
    chipEdge = LagoonEdge,
    dash = LagoonEdge,
    card = LagoonCard,
    onCard = Foam,
    pill = Mango,
    onPill = PetrolDeep,
    missing = TomatoNight,
)

private val LightScheme = lightColorScheme(
    primary = Petrol,
    onPrimary = Cream,
    primaryContainer = MangoSoft,
    onPrimaryContainer = PetrolDeep,
    secondary = Mango,
    onSecondary = PetrolDeep,
    secondaryContainer = MangoSoft,
    onSecondaryContainer = PetrolDeep,
    background = Cream,
    onBackground = PetrolDeep,
    surface = Cream,
    onSurface = PetrolDeep,
    surfaceVariant = White,
    onSurfaceVariant = Olive,
    surfaceContainerLowest = White,
    surfaceContainerLow = White,
    surfaceContainer = White,
    surfaceContainerHigh = White,
    surfaceContainerHighest = CreamEdge,
    outline = Dash,
    outlineVariant = CreamEdge,
    error = Tomato,
    onError = White,
)

private val DarkScheme = darkColorScheme(
    primary = Mango,
    onPrimary = PetrolDeep,
    primaryContainer = LagoonCard,
    onPrimaryContainer = Foam,
    secondary = Mango,
    onSecondary = PetrolDeep,
    secondaryContainer = LagoonCard,
    onSecondaryContainer = Mango,
    background = Lagoon,
    onBackground = Foam,
    surface = Lagoon,
    onSurface = Foam,
    surfaceVariant = LagoonCard,
    onSurfaceVariant = Reed,
    surfaceContainerLowest = Lagoon,
    surfaceContainerLow = LagoonCard,
    surfaceContainer = LagoonCard,
    surfaceContainerHigh = LagoonCard,
    surfaceContainerHighest = LagoonEdge,
    outline = LagoonEdge,
    outlineVariant = LagoonEdge,
    error = TomatoNight,
    onError = Lagoon,
)

val LocalMango = staticCompositionLocalOf { LightMango }

object ChefYa {
    val colors: MangoColors
        @Composable @ReadOnlyComposable get() = LocalMango.current
}

@Composable
fun isDark(themeMode: ThemeMode): Boolean = when (themeMode) {
    ThemeMode.SYSTEM -> isSystemInDarkTheme()
    ThemeMode.LIGHT -> false
    ThemeMode.DARK -> true
}

@Composable
fun ChefYaTheme(themeMode: ThemeMode = ThemeMode.SYSTEM, content: @Composable () -> Unit) {
    val dark = isDark(themeMode)
    CompositionLocalProvider(LocalMango provides if (dark) DarkMango else LightMango) {
        MaterialTheme(
            colorScheme = if (dark) DarkScheme else LightScheme,
            typography = ChefTypography,
            content = content,
        )
    }
}
