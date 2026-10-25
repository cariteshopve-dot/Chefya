package agency.lacosta.chefya.domain

/** Decide qué se puede cocinar con lo que hay, sin comprar casi nada. */
object Matcher {

    /** Lo máximo que puede faltar para que una receta salga en "Puedes cocinar". */
    const val MAX_MISSING = 2

    fun compare(recipe: Recipe, pantry: Set<String>): Suggestion {
        val needed = recipe.ingredients.filterNot(Ingredients::isStaple)
        return Suggestion(
            recipe = recipe,
            have = needed.filter { it in pantry },
            missing = needed.filterNot { it in pantry },
        )
    }

    /**
     * Las recetas que usan algo de la nevera y a las que les faltan pocas cosas. Primero las que
     * se pueden hacer ya, luego las que usan más de lo que hay, y entre iguales la más rápida.
     */
    fun suggest(pantry: Set<String>, recipes: List<Recipe> = Recipes.all): List<Suggestion> =
        recipes.map { compare(it, pantry) }
            .filter { it.have.isNotEmpty() && it.missing.size <= MAX_MISSING }
            .sortedWith(order)

    /** Todas las recetas, en el mismo orden, para la pestaña Recetas. */
    fun rankAll(pantry: Set<String>, recipes: List<Recipe> = Recipes.all): List<Suggestion> =
        recipes.map { compare(it, pantry) }.sortedWith(order)

    private val order = compareBy<Suggestion>({ it.missing.size }, { -it.have.size }, { it.recipe.minutes })
}
