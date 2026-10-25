package agency.lacosta.chefya.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import agency.lacosta.chefya.R

@OptIn(ExperimentalTextApi::class)
private fun fredoka(weight: Int) =
    Font(R.font.fredoka, FontWeight(weight), variationSettings = FontVariation.Settings(FontVariation.weight(weight)))

/** Fredoka para todo: redonda y amable, como la cocina de la casa. */
val Fredoka = FontFamily(fredoka(400), fredoka(500), fredoka(600), fredoka(700))

private val base = Typography()

private fun TextStyle.fredoka(weight: FontWeight) = copy(fontFamily = Fredoka, fontWeight = weight)

val ChefTypography = Typography(
    displayLarge = base.displayLarge.fredoka(FontWeight.Bold),
    displayMedium = base.displayMedium.fredoka(FontWeight.Bold),
    displaySmall = base.displaySmall.fredoka(FontWeight.Bold),
    headlineLarge = base.headlineLarge.fredoka(FontWeight.Bold),
    headlineMedium = base.headlineMedium.fredoka(FontWeight.Bold),
    headlineSmall = base.headlineSmall.fredoka(FontWeight.SemiBold),
    titleLarge = base.titleLarge.fredoka(FontWeight.SemiBold),
    titleMedium = base.titleMedium.fredoka(FontWeight.SemiBold),
    titleSmall = base.titleSmall.fredoka(FontWeight.Medium),
    bodyLarge = base.bodyLarge.fredoka(FontWeight.Normal),
    bodyMedium = base.bodyMedium.fredoka(FontWeight.Normal),
    bodySmall = base.bodySmall.fredoka(FontWeight.Normal),
    labelLarge = base.labelLarge.fredoka(FontWeight.Medium),
    labelMedium = base.labelMedium.fredoka(FontWeight.Medium),
    labelSmall = base.labelSmall.fredoka(FontWeight.Medium),
)
