package agency.lacosta.chefya.ui.shopping

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import agency.lacosta.chefya.R
import agency.lacosta.chefya.domain.PantryItem
import agency.lacosta.chefya.ui.KitchenState
import agency.lacosta.chefya.ui.components.AddIngredientField
import agency.lacosta.chefya.ui.label
import agency.lacosta.chefya.ui.language
import agency.lacosta.chefya.ui.theme.ChefYa

/** La lista de compras. Marcar algo como comprado lo pasa a la nevera. */
@Composable
fun ShoppingScreen(
    state: KitchenState,
    onAdd: (PantryItem) -> Unit,
    onBought: (PantryItem) -> Unit,
    onRemove: (PantryItem) -> Unit,
) {
    val lang = language()
    LazyColumn(
        modifier = Modifier.fillMaxSize().statusBarsPadding().imePadding(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(R.string.shopping_title), style = MaterialTheme.typography.headlineMedium)
                Text(
                    stringResource(R.string.shopping_sub),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item {
            AddIngredientField(
                existing = state.shoppingKeys,
                onAdd = onAdd,
                placeholder = stringResource(R.string.shopping_add_hint),
            )
        }
        if (state.shopping.isEmpty()) {
            item {
                Text(
                    stringResource(R.string.shopping_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        items(state.shopping.sortedBy { it.label(lang).lowercase() }, key = { it.key }) { item ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ChefYa.colors.chip,
                border = BorderStroke(1.dp, ChefYa.colors.chipEdge),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 4.dp)) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { onBought(item) },
                        colors = CheckboxDefaults.colors(uncheckedColor = ChefYa.colors.dash),
                    )
                    Text(item.label(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    IconButton(onClick = { onRemove(item) }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.remove_item, item.label()),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}
