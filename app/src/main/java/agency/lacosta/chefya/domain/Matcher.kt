package agency.lacosta.chefya.domain

/**
 * Decide qué se puede cocinar con lo que hay, sin comprar casi nada.
 *
 * Una receta se sugiere por su ingrediente principal (el huevo del perico, las lentejas de la
 * sopa), nunca solo porque comparte la cebolla o el ajo. Los de sazón pueden faltar un poco;
 * los principales casi nada.
 */
object Matcher {

    /** Cuántos principales pueden faltar para que una receta salga como "te falta poco". */
    const val MAX_MISSING_MAIN = 1

    /** Cuántos de sazón (cebolla, ajo, tomate...) pueden faltar. */
    const val MAX_MISSING_SEASONING = 3

    fun compare(recipe: Recipe, pantry: Set<String>): Suggestion {
        val needed = recipe.ingredients.filterNot(Ingredients::isStaple)
        return Suggestion(
            recipe = recipe,
            have = needed.filter { it in pantry },
            missing = needed.filterNot { it in pantry },
        )
    }

    /**
     * Las recetas cuyo plato principal ya está en la nevera. Si falta un principal solo cuenta
     * cuando la receta tiene otro y ese sí está. Primero las que se pueden hacer ya, luego las
     * que piden comprar menos, luego las que usan más de lo que hay, y entre iguales la más rápida.
     */
    fun suggest(pantry: Set<String>, recipes: List<Recipe> = Recipes.all): List<Suggestion> =
        recipes.map { compare(it, pantry) }
            .filter { fits(it) }
            .sortedWith(order)

    /** Todas las recetas, en el mismo orden, para la pestaña Recetas. */
    fun rankAll(pantry: Set<String>, recipes: List<Recipe> = Recipes.all): List<Suggestion> =
        recipes.map { compare(it, pantry) }.sortedWith(order)

    private fun fits(s: Suggestion): Boolean {
        val haveMain = s.have.count { !Ingredients.isSeasoning(it) }
        val missingSeasoning = s.missing.size - s.missingMain.size
        return haveMain >= 1 && s.missingMain.size <= MAX_MISSING_MAIN && missingSeasoning <= MAX_MISSING_SEASONING
    }

    private val order = compareBy<Suggestion>(
        { it.missingMain.size },
        { it.missing.size },
        { -it.have.size },
        { it.recipe.minutes },
    )
}
