package agency.lacosta.chefya.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MatcherTest {

    private fun ids(vararg pantry: String) = Matcher.suggest(pantry.toSet()).map { it.recipe.id }

    @Test
    fun staplesNeverCountAsMissing() {
        val perico = Recipes.byId("perico")!!
        val s = Matcher.compare(perico, setOf("huevo", "tomate", "cebolla"))
        assertTrue(s.ready)
        assertEquals(listOf("huevo", "tomate", "cebolla"), s.have)
    }

    @Test
    fun seasoningAloneSuggestsNothing() {
        // Antes, tener cebolla o limón bastaba para sugerir perico o yuca con mojo.
        assertTrue(ids("cebolla").isEmpty())
        assertTrue(ids("ajo", "limon", "cebolla", "tomate").isEmpty())
    }

    @Test
    fun aRecipeNeedsItsMainIngredient() {
        val result = ids("pasta", "queso", "ajo")
        assertFalse("yuca_mojo" in result)
        assertFalse("sandwich_plancha" in result)
        assertTrue("pasta_atun" in result)
    }

    @Test
    fun missingSeasoningIsFineMissingTwoMainsIsNot() {
        assertEquals(listOf("sopa_lentejas"), ids("lentejas"))
        // Avena sola: a las dos recetas con avena les faltan dos principales.
        assertTrue(ids("avena").isEmpty())
    }

    @Test
    fun readyRecipesComeFirst() {
        val result = Matcher.suggest(setOf("huevo", "arroz", "cebolla", "tomate"))
        assertEquals(setOf("perico", "arroz_huevo"), result.takeWhile { it.ready }.map { it.recipe.id }.toSet())
        assertTrue(result.all { it.missingMain.size <= Matcher.MAX_MISSING_MAIN })
    }

    @Test
    fun recipesWithAllMainsComeBeforeOnesThatNeedShopping() {
        val result = Matcher.suggest(setOf("pollo", "arroz"))
        assertEquals("arroz_pollo", result.first().recipe.id)
        val firstNeedingMain = result.indexOfFirst { it.missingMain.isNotEmpty() }
        assertTrue(result.drop(firstNeedingMain).all { it.missingMain.isNotEmpty() })
    }

    @Test
    fun emptyPantrySuggestsNothingButRankAllListsEverything() {
        assertTrue(Matcher.suggest(emptySet()).isEmpty())
        assertEquals(Recipes.all.size, Matcher.rankAll(emptySet()).size)
    }
}
