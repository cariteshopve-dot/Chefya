package agency.lacosta.chefya.domain

/**
 * Una receta casera. [ingredients] son los que hacen falta de verdad; [optional] la mejoran si
 * los hay. Los básicos (sal, aceite, agua...) pueden aparecer en la lista pero nunca faltan.
 */
data class Recipe(
    val id: String,
    val name: Text,
    val minutes: Int,
    val ingredients: List<String>,
    val optional: List<String> = emptyList(),
    val steps: List<Text>,
)

/** Una receta vista contra lo que hay en la nevera. */
data class Suggestion(
    val recipe: Recipe,
    val have: List<String>,
    val missing: List<String>,
) {
    val ready: Boolean get() = missing.isEmpty()

    /** Lo que falta que sí define el plato; la sazón que falta no cuenta aquí. */
    val missingMain: List<String> get() = missing.filterNot(Ingredients::isSeasoning)
}
