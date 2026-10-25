package agency.lacosta.chefya.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogTest {

    @Test
    fun everyRecipeUsesKnownIngredientsAndHasBothLanguages() {
        for (recipe in Recipes.all) {
            for (id in recipe.ingredients + recipe.optional) {
                assertNotNull("${recipe.id} usa $id", Ingredients.byId(id))
            }
            assertTrue(recipe.id, recipe.name.es.isNotBlank() && recipe.name.en.isNotBlank())
            assertTrue(recipe.id, recipe.steps.isNotEmpty())
            assertTrue(recipe.id, recipe.steps.all { it.es.isNotBlank() && it.en.isNotBlank() })
            assertTrue("${recipe.id} tarda ${recipe.minutes}", recipe.minutes in 1..30)
        }
    }

    @Test
    fun idsAreUnique() {
        assertEquals(Recipes.all.size, Recipes.all.map { it.id }.toSet().size)
        assertEquals(Ingredients.all.size, Ingredients.all.map { it.id }.toSet().size)
    }
}
