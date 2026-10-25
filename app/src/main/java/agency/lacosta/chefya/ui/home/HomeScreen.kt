package agency.lacosta.chefya.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.domain.PantryItem
import agency.lacosta.chefya.ui.KitchenState
import agency.lacosta.chefya.ui.components.AddIngredientField
import agency.lacosta.chefya.ui.components.IngredientChip
import agency.lacosta.chefya.ui.components.RecipeCard
import agency.lacosta.chefya.ui.label
import agency.lacosta.chefya.ui.language
import agency.lacosta.chefya.ui.theme.ChefYa
import java.time.LocalTime

/** Cuántas recetas se muestran en Inicio; el resto está en la pestaña Recetas. */
private const val HOME_RECIPES = 5

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    state: KitchenState,
    onAdd: (PantryItem) -> Unit,
    onRemove: (PantryItem) -> Unit,
    onOpenRecipe: (String) -> Unit,
    onSeeAll: () -> Unit,
    say: (String) -> Unit,
) {
    val photoSoon = stringResource(R.string.photo_soon)
    val lang = language()
    LazyColumn(
        modifier = Modifier.fillMaxSize().imePadding(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Hero(onPhoto = { say(photoSoon) }) }

        item {
            Section(
                if (state.pantry.isEmpty()) stringResource(R.string.pantry_empty_title)
                else stringResource(R.string.pantry_title, state.pantry.size),
            )
        }
        item {
            Column(Modifier.padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (state.pantry.isNotEmpty()) {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        state.pantry.sortedBy { it.label(lang).lowercase() }.forEach { item ->
                            IngredientChip(item.label(), onRemove = { onRemove(item) }, unknown = item is PantryItem.Custom)
                        }
                    }
                    val unknown = state.pantry.filterIsInstance<PantryItem.Custom>()
                    if (unknown.isNotEmpty()) {
                        Text(
                            stringResource(R.string.unknown_items, unknown.joinToString(", ") { it.label }),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                AddIngredientField(
                    existing = state.pantryKeys,
                    onAdd = onAdd,
                    placeholder = stringResource(R.string.pantry_add_hint),
                )
            }
        }
        if (state.pantry.isEmpty()) {
            item { Hint(stringResource(R.string.pantry_empty_hint)) }
        }

        if (state.pantry.isNotEmpty()) {
            val ready = state.suggestions.filter { it.missingMain.isEmpty() }
            val almost = state.suggestions.filter { it.missingMain.isNotEmpty() }
            item {
                Row(
                    Modifier.fillMaxWidth().padding(start = 18.dp, end = 8.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(R.string.can_cook), style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    TextButton(onClick = onSeeAll) { Text(stringResource(R.string.see_all)) }
                }
            }
            if (ready.isEmpty()) {
                item { Hint(stringResource(if (almost.isEmpty()) R.string.no_matches else R.string.no_ready)) }
            }
            items(ready.take(HOME_RECIPES), key = { it.recipe.id }) { suggestion ->
                RecipeCard(suggestion, onClick = { onOpenRecipe(suggestion.recipe.id) }, modifier = Modifier.padding(horizontal = 18.dp))
            }
            if (almost.isNotEmpty()) {
                item { Section(stringResource(R.string.buy_one_more)) }
                items(almost.take(HOME_RECIPES), key = { it.recipe.id }) { suggestion ->
                    RecipeCard(suggestion, onClick = { onOpenRecipe(suggestion.recipe.id) }, modifier = Modifier.padding(horizontal = 18.dp))
                }
            }
        }
    }
}

@Composable
private fun Hero(onPhoto: () -> Unit) {
    val colors = ChefYa.colors
    Column(
        Modifier
            .fillMaxWidth()
            .background(colors.hero, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .statusBarsPadding()
            .padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(stringResource(greeting()), style = MaterialTheme.typography.bodyLarge, color = colors.onHero)
        Text(stringResource(R.string.hero_title), style = MaterialTheme.typography.headlineMedium, color = colors.onHero)
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = colors.card,
            contentColor = colors.onCard,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).clickable(onClick = onPhoto),
        ) {
            Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    Modifier.size(38.dp).background(colors.pill, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(painterResource(R.drawable.ic_camera), contentDescription = null, tint = colors.onPill, modifier = Modifier.size(22.dp))
                }
                Column {
                    Text(stringResource(R.string.photo_button), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.photo_button_sub), style = MaterialTheme.typography.bodySmall, color = colors.onCard.copy(alpha = 0.75f))
                }
            }
        }
    }
}

@Composable
private fun Section(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 6.dp))
}

@Composable
private fun Hint(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 18.dp),
    )
}

private fun greeting(): Int = when (LocalTime.now().hour) {
    in 5..11 -> R.string.greeting_morning
    in 12..18 -> R.string.greeting_afternoon
    else -> R.string.greeting_evening
}
