package agency.lacosta.chefya.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.ui.KitchenState
import agency.lacosta.chefya.ui.components.RecipeCard

/** Todas las recetas de la app, primero las que mejor encajan con lo que hay. */
@Composable
fun RecipesScreen(state: KitchenState, onOpenRecipe: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(stringResource(R.string.recipes_title), style = MaterialTheme.typography.headlineMedium)
            Text(
                stringResource(R.string.recipes_sub),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        items(state.allRecipes, key = { it.recipe.id }) { suggestion ->
            RecipeCard(suggestion, onClick = { onOpenRecipe(suggestion.recipe.id) })
        }
    }
}
