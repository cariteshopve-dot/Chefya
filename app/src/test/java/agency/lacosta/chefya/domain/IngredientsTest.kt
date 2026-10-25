package agency.lacosta.chefya.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class IngredientsTest {

    @Test
    fun findsPluralsAccentsAndCountryNames() {
        assertEquals("huevo", Ingredients.find("Huevos")?.id)
        assertEquals("tomate", Ingredients.find("tomates")?.id)
        assertEquals("limon", Ingredients.find("LIMONES")?.id)
        assertEquals("atun", Ingredients.find("atún")?.id)
        assertEquals("caraotas", Ingredients.find("frijoles")?.id)
        assertEquals("caraotas", Ingredients.find("porotos")?.id)
        assertEquals("aguacate", Ingredients.find("palta")?.id)
        assertEquals("papa", Ingredients.find("patatas")?.id)
        assertEquals("harina_maiz", Ingredients.find("Harina PAN")?.id)
        assertEquals("pimenton", Ingredients.find("pimientos")?.id)
        assertEquals("pimienta", Ingredients.find("pimienta")?.id)
        assertEquals("platano", Ingredients.find("plátano")?.id)
        assertEquals("banana", Ingredients.find("cambur")?.id)
        assertEquals("huevo", Ingredients.find("eggs")?.id)
        assertEquals("caraotas", Ingredients.find("frijol")?.id)
        assertEquals("caraotas", Ingredients.find("caraota")?.id)
        assertEquals("carne_res", Ingredients.find("Carne")?.id)
        assertEquals("carne_molida", Ingredients.find("carne molida")?.id)
        assertEquals("pescado", Ingredients.find("pescado")?.id)
        assertEquals("lentejas", Ingredients.find("lenteja")?.id)
    }

    @Test
    fun unknownTextIsKeptAsCustom() {
        assertNull(Ingredients.find("salsa de soya"))
        val item = PantryItem.fromInput("  salsa   de soya ")
        assertEquals(PantryItem.Custom("salsa de soya"), item)
        assertEquals(item, PantryItem.fromKey(item!!.key))
        assertNull(PantryItem.fromInput("   "))
    }

    @Test
    fun knownItemsRoundTrip() {
        val item = PantryItem.fromInput("Huevos")!!
        assertEquals("huevo", item.key)
        assertEquals(item, PantryItem.fromKey("huevo"))
        assertNull(PantryItem.fromKey("no-existe"))
    }

    @Test
    fun customItemsSavedBeforeAreRecognizedNow() {
        assertEquals("carne_res", PantryItem.fromKey("custom:carne")?.key)
        assertEquals("custom:salsa de soya", PantryItem.fromKey("custom:salsa de soya")?.key)
    }

    @Test
    fun suggestsWhileTypingWithoutStaples() {
        val ids = Ingredients.suggest("ce").map { it.id }
        assertTrue("cebolla" in ids)
        assertTrue(Ingredients.suggest("sa").none { it.staple })
        assertTrue(Ingredients.suggest("").isEmpty())
    }
}
