package agency.lacosta.chefya.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MatcherTest {

    @Test
    fun staplesNeverCountAsMissing() {
        val perico = Recipes.byId("perico")!!
        val s = Matcher.compare(perico, setOf("huevo", "tomate", "cebolla"))
        assertTrue(s.ready)
        assertEquals(listOf("huevo", "tomate", "cebolla"), s.have)
    }

    @Test
    fun readyRecipesComeFirstUsingMoreOfThePantry() {
        val result = Matcher.suggest(setOf("huevo", "tomate", "cebolla", "tortilla", "queso"))
        val ready = result.takeWhile { it.ready }.map { it.recipe.id }
        assertEquals(listOf("huevos_rancheros", "perico", "quesadillas"), ready)
        assertTrue(result.all { it.missing.size <= Matcher.MAX_MISSING })
    }

    @Test
    fun betweenEqualMatchesTheFastestWins() {
        val result = Matcher.suggest(setOf("arroz", "huevo", "cebolla", "zanahoria", "atun", "tomate", "limon"))
        val ids = result.map { it.recipe.id }
        // Ensalada de atún (10 min) y arroz frito (20 min) están listas y usan 4 cosas cada una.
        assertTrue(ids.indexOf("ensalada_atun") < ids.indexOf("arroz_frito"))
    }

    @Test
    fun recipesThatUseNothingFromThePantryAreHidden() {
        val result = Matcher.suggest(setOf("avena"))
        assertFalse(result.any { it.recipe.id == "arepas" })
        assertTrue(result.any { it.recipe.id == "panquecas_cambur" })
    }

    @Test
    fun emptyPantrySuggestsNothingButRankAllListsEverything() {
        assertTrue(Matcher.suggest(emptySet()).isEmpty())
        assertEquals(Recipes.all.size, Matcher.rankAll(emptySet()).size)
    }
}
