package agency.lacosta.chefya.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.domain.Ingredients
import agency.lacosta.chefya.domain.Matcher
import agency.lacosta.chefya.domain.Recipes
import agency.lacosta.chefya.ui.KitchenState
import agency.lacosta.chefya.ui.components.MinutesPill
import agency.lacosta.chefya.ui.ingredientLabel
import agency.lacosta.chefya.ui.localized
import agency.lacosta.chefya.ui.theme.ChefYa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    recipeId: String,
    state: KitchenState,
    onBack: () -> Unit,
    onAddMissing: (List<String>) -> Unit,
    say: (String) -> Unit,
) {
    val recipe = Recipes.byId(recipeId)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
    ) { padding ->
        if (recipe == null) {
            Text(stringResource(R.string.recipe_missing), modifier = Modifier.padding(padding).padding(18.dp))
            return@Scaffold
        }
        val match = Matcher.compare(recipe, state.pantryKeys)
        val toBuy = match.missing.filterNot { it in state.shoppingKeys }
        val addedMessage = stringResource(R.string.added_to_shopping)
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    MinutesPill(recipe.minutes)
                    Text(recipe.name.localized(), style = MaterialTheme.typography.headlineMedium)
                }
            }
            item { Heading(stringResource(R.string.ingredients)) }
            recipe.ingredients.forEach { id ->
                item(key = "i-$id") {
                    val has = id in state.pantryKeys || Ingredients.isStaple(id)
                    IngredientLine(ingredientLabel(id), has = has, staple = Ingredients.isStaple(id) && id !in state.pantryKeys)
                }
            }
            if (recipe.optional.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.optional, recipe.optional.map { ingredientLabel(it) }.joinToString(", ")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            if (match.missing.isNotEmpty()) {
                item {
                    Button(
                        onClick = {
                            onAddMissing(match.missing)
                            say(addedMessage)
                        },
                        enabled = toBuy.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = ChefYa.colors.card, contentColor = ChefYa.colors.onCard),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            if (toBuy.isEmpty()) stringResource(R.string.already_in_shopping)
                            else stringResource(R.string.add_missing, toBuy.size),
                        )
                    }
                }
            }
            item { Heading(stringResource(R.string.steps)) }
            // Los pasos sí van en orden, así que se numeran.
            itemsIndexed(recipe.steps) { index, step ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        Modifier.size(28.dp).background(ChefYa.colors.pill, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("${index + 1}", style = MaterialTheme.typography.labelLarge, color = ChefYa.colors.onPill)
                    }
                    Text(step.localized(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f).padding(top = 2.dp))
                }
            }
        }
    }
}

@Composable
private fun Heading(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 10.dp))
}

@Composable
private fun IngredientLine(label: String, has: Boolean, staple: Boolean) {
    val colors = ChefYa.colors
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            Modifier
                .size(24.dp)
                .background(if (has) colors.card else colors.missing.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (has) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = null,
                tint = if (has) colors.onCard else colors.missing,
                modifier = Modifier.size(16.dp),
            )
        }
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        when {
            staple -> Text(stringResource(R.string.staple), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            !has -> Text(stringResource(R.string.missing), style = MaterialTheme.typography.bodySmall, color = colors.missing)
        }
    }
}
