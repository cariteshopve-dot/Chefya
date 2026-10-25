package agency.lacosta.chefya.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.domain.Ingredients
import agency.lacosta.chefya.domain.PantryItem
import agency.lacosta.chefya.domain.Suggestion
import agency.lacosta.chefya.ui.ingredientLabel
import agency.lacosta.chefya.ui.localized
import agency.lacosta.chefya.ui.theme.ChefYa

/**
 * Un ingrediente como ficha blanca con borde de papel; con [onRemove] lleva una X para quitarlo.
 * Si la app no lo reconoce ([unknown]) la ficha va transparente con borde más marcado.
 */
@Composable
fun IngredientChip(label: String, onRemove: (() -> Unit)? = null, onClick: (() -> Unit)? = null, unknown: Boolean = false) {
    val colors = ChefYa.colors
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (unknown) Color.Transparent else colors.chip,
        border = if (unknown) BorderStroke(1.5.dp, colors.dash) else BorderStroke(1.dp, colors.chipEdge),
        modifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, end = if (onRemove != null) 4.dp else 12.dp),
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 7.dp))
            if (onRemove != null) {
                IconButton(onClick = onRemove, modifier = Modifier.size(30.dp)) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = stringResource(R.string.remove_item, label),
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

/** Ficha punteada que propone un ingrediente mientras se escribe. */
@Composable
private fun SuggestionChip(label: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        border = BorderStroke(1.5.dp, ChefYa.colors.dash),
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Text(
            "+ $label",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
        )
    }
}

/**
 * Campo para escribir un ingrediente. Mientras se escribe propone los que la app conoce; lo que
 * no conoce también se puede agregar tal cual.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddIngredientField(
    existing: Set<String>,
    onAdd: (PantryItem) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf("") }
    val submit = {
        PantryItem.fromInput(text)?.let(onAdd)
        text = ""
    }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(placeholder) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { submit() }),
            trailingIcon = {
                IconButton(onClick = submit, enabled = text.isNotBlank()) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add))
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = ChefYa.colors.chip,
                unfocusedContainerColor = ChefYa.colors.chip,
                unfocusedBorderColor = ChefYa.colors.chipEdge,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        val proposals = Ingredients.suggest(text).filterNot { it.id in existing }
        if (proposals.isNotEmpty()) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                proposals.forEach { ingredient ->
                    SuggestionChip(ingredient.name.localized()) {
                        onAdd(PantryItem.Known(ingredient))
                        text = ""
                    }
                }
            }
        }
    }
}

/** Tarjeta petróleo de una receta: tiempo, nombre y qué tanto se puede hacer con lo que hay. */
@Composable
fun RecipeCard(suggestion: Suggestion, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = ChefYa.colors
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = colors.card,
        contentColor = colors.onCard,
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            MinutesPill(suggestion.recipe.minutes)
            Text(suggestion.recipe.name.localized(), style = MaterialTheme.typography.titleLarge)
            Text(matchLine(suggestion), style = MaterialTheme.typography.bodyMedium, color = colors.onCard.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun MinutesPill(minutes: Int) {
    Surface(shape = RoundedCornerShape(50), color = ChefYa.colors.pill, contentColor = ChefYa.colors.onPill) {
        Text(
            stringResource(R.string.minutes, minutes),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun matchLine(s: Suggestion): String = when {
    s.ready -> stringResource(R.string.match_ready, s.have.size)
    else -> stringResource(R.string.match_missing, s.missing.map { ingredientLabel(it) }.joinToString(", "))
}
