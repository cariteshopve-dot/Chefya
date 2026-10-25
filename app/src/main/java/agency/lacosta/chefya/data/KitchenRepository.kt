package agency.lacosta.chefya.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class ThemeMode { SYSTEM, LIGHT, DARK }

data class Settings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    /** Suscripción activa. Por ahora no se puede activar; luego lo pondrá Google Play Billing. */
    val isPro: Boolean = false,
)

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kitchen")

/**
 * Todo queda en el teléfono: lo que hay en la nevera y la lista de compras, guardados como
 * claves de [agency.lacosta.chefya.domain.PantryItem].
 */
class KitchenRepository(context: Context) {

    private val store = context.dataStore

    val pantry: Flow<Set<String>> = store.data.map { it[PANTRY] ?: emptySet() }

    val shopping: Flow<Set<String>> = store.data.map { it[SHOPPING] ?: emptySet() }

    val settings: Flow<Settings> = store.data.map { prefs ->
        Settings(
            themeMode = prefs[THEME]?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() } ?: ThemeMode.SYSTEM,
            isPro = prefs[PRO] ?: false,
        )
    }

    suspend fun addToPantry(keys: Collection<String>) = editSet(PANTRY) { it + keys }

    suspend fun removeFromPantry(key: String) = editSet(PANTRY) { it - key }

    suspend fun clearPantry() = editSet(PANTRY) { emptySet() }

    suspend fun addToShopping(keys: Collection<String>) = editSet(SHOPPING) { it + keys }

    suspend fun removeFromShopping(key: String) = editSet(SHOPPING) { it - key }

    /** Lo comprado sale de la lista y entra a la nevera en un solo paso. */
    suspend fun markBought(key: String) {
        store.edit {
            it[SHOPPING] = (it[SHOPPING] ?: emptySet()) - key
            it[PANTRY] = (it[PANTRY] ?: emptySet()) + key
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        store.edit { it[THEME] = mode.name }
    }

    private suspend fun editSet(key: Preferences.Key<Set<String>>, change: (Set<String>) -> Set<String>) {
        store.edit { it[key] = change(it[key] ?: emptySet()) }
    }

    private companion object {
        val PANTRY = stringSetPreferencesKey("pantry")
        val SHOPPING = stringSetPreferencesKey("shopping")
        val THEME = stringPreferencesKey("theme_mode")
        val PRO = booleanPreferencesKey("is_pro")
    }
}
