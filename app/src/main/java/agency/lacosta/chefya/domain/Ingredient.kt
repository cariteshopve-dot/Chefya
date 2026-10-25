package agency.lacosta.chefya.domain

/**
 * Un ingrediente que la app conoce. [aliases] son otros nombres del mismo ingrediente en
 * distintos países (caraotas, frijoles, porotos...). Los de [staple] se dan por hechos en
 * cualquier cocina y nunca salen como "te falta".
 */
data class Ingredient(
    val id: String,
    val name: Text,
    val aliases: List<String> = emptyList(),
    val staple: Boolean = false,
)

/** Lo que hay en la nevera: un ingrediente conocido o algo escrito a mano que la app no reconoce. */
sealed interface PantryItem {
    val key: String

    data class Known(val ingredient: Ingredient) : PantryItem {
        override val key: String get() = ingredient.id
    }

    data class Custom(val label: String) : PantryItem {
        override val key: String get() = CUSTOM_PREFIX + label
    }

    companion object {
        const val CUSTOM_PREFIX = "custom:"

        /** Lo inverso de [key]: lo que se guardó en el teléfono vuelve a ser un ingrediente. */
        fun fromKey(key: String): PantryItem? = when {
            key.startsWith(CUSTOM_PREFIX) -> key.removePrefix(CUSTOM_PREFIX).takeIf { it.isNotBlank() }?.let(::Custom)
            else -> Ingredients.byId(key)?.let(::Known)
        }

        /** Lo que alguien escribió: si la app lo reconoce usa el ingrediente, si no lo guarda tal cual. */
        fun fromInput(raw: String): PantryItem? {
            val label = raw.trim().replace(Regex("\\s+"), " ")
            if (label.isEmpty()) return null
            return Ingredients.find(label)?.let(::Known) ?: Custom(label)
        }
    }
}
