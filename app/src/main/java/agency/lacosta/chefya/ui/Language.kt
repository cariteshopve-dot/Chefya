package agency.lacosta.chefya.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import agency.lacosta.chefya.domain.Ingredients
import agency.lacosta.chefya.domain.PantryItem
import agency.lacosta.chefya.domain.Text

/** El idioma del teléfono, en dos letras ("es", "en"...). */
@Composable
@ReadOnlyComposable
fun language(): String = LocalConfiguration.current.locales[0].language

@Composable
@ReadOnlyComposable
fun Text.localized(): String = get(language())

fun PantryItem.label(language: String): String = when (this) {
    is PantryItem.Known -> ingredient.name.get(language)
    is PantryItem.Custom -> label
}

@Composable
@ReadOnlyComposable
fun PantryItem.label(): String = label(language())

@Composable
@ReadOnlyComposable
fun ingredientLabel(id: String): String = Ingredients.byId(id)?.name?.localized() ?: id
