package agency.lacosta.chefya.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import agency.lacosta.chefya.ChefYaApp
import agency.lacosta.chefya.data.KitchenRepository
import agency.lacosta.chefya.data.Settings
import agency.lacosta.chefya.data.ThemeMode
import agency.lacosta.chefya.domain.Matcher
import agency.lacosta.chefya.domain.PantryItem
import agency.lacosta.chefya.domain.Suggestion
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class KitchenState(
    val pantry: List<PantryItem> = emptyList(),
    val shopping: List<PantryItem> = emptyList(),
    /** Lo que se puede cocinar ya o casi ya, para Inicio. */
    val suggestions: List<Suggestion> = emptyList(),
    /** Todas las recetas en el mismo orden, para la pestaña Recetas. */
    val allRecipes: List<Suggestion> = emptyList(),
    val settings: Settings = Settings(),
) {
    val pantryKeys: Set<String> get() = pantry.mapTo(HashSet()) { it.key }
    val shoppingKeys: Set<String> get() = shopping.mapTo(HashSet()) { it.key }
}

/** Una sola vista de la cocina para todas las pantallas: son pocas y comparten los mismos datos. */
class ChefYaViewModel(private val kitchen: KitchenRepository) : ViewModel() {

    init {
        launch { kitchen.upgradeKeys() }
    }

    val state: StateFlow<KitchenState> =
        combine(kitchen.pantry, kitchen.shopping, kitchen.settings) { pantryKeys, shoppingKeys, settings ->
            val known = pantryKeys.filterTo(HashSet()) { !it.startsWith(PantryItem.CUSTOM_PREFIX) }
            KitchenState(
                pantry = pantryKeys.mapNotNull(PantryItem::fromKey),
                shopping = shoppingKeys.mapNotNull(PantryItem::fromKey),
                suggestions = Matcher.suggest(known),
                allRecipes = Matcher.rankAll(known),
                settings = settings,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), KitchenState())

    fun addToPantry(item: PantryItem) = launch { kitchen.addToPantry(listOf(item.key)) }

    fun removeFromPantry(item: PantryItem) = launch { kitchen.removeFromPantry(item.key) }

    fun clearPantry() = launch { kitchen.clearPantry() }

    fun addToShopping(keys: Collection<String>) = launch { kitchen.addToShopping(keys) }

    fun addToShopping(item: PantryItem) = addToShopping(listOf(item.key))

    fun removeFromShopping(item: PantryItem) = launch { kitchen.removeFromShopping(item.key) }

    fun markBought(item: PantryItem) = launch { kitchen.markBought(item.key) }

    fun setThemeMode(mode: ThemeMode) = launch { kitchen.setThemeMode(mode) }

    private fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { ChefYaViewModel((this[APPLICATION_KEY] as ChefYaApp).container.kitchen) }
        }
    }
}
