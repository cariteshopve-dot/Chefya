package agency.lacosta.chefya.domain

/** Ingredientes de la cocina casera latina, con los nombres que se usan en cada país. */
object Ingredients {

    val all: List<Ingredient> = listOf(
        Ingredient("huevo", Text("Huevos", "Eggs"), listOf("huevo", "egg")),
        Ingredient("tomate", Text("Tomate", "Tomato"), listOf("jitomate")),
        Ingredient("cebolla", Text("Cebolla", "Onion")),
        Ingredient("ajo", Text("Ajo", "Garlic")),
        Ingredient("pimenton", Text("Pimentón", "Bell pepper"), listOf("pimiento", "aji dulce", "chile morron", "morron", "pepper")),
        Ingredient("arroz", Text("Arroz", "Rice")),
        Ingredient("pasta", Text("Pasta", "Pasta"), listOf("espaguetis", "espagueti", "fideos", "spaghetti", "noodles", "macarrones")),
        Ingredient("harina_maiz", Text("Harina de maíz", "Corn flour"), listOf("harina pan", "harina precocida", "masa de maiz", "masarepa", "cornmeal")),
        Ingredient("harina_trigo", Text("Harina de trigo", "Wheat flour"), listOf("harina", "flour")),
        Ingredient("pan", Text("Pan", "Bread"), listOf("pan de molde", "pan sobado")),
        Ingredient("tortilla", Text("Tortillas", "Tortillas"), listOf("tortillas de maiz", "tortillas de harina")),
        Ingredient("queso", Text("Queso", "Cheese"), listOf("queso blanco", "queso fresco", "queso amarillo", "queso rallado")),
        Ingredient("leche", Text("Leche", "Milk")),
        Ingredient("mantequilla", Text("Mantequilla", "Butter"), listOf("margarina", "manteca")),
        Ingredient("pollo", Text("Pollo", "Chicken"), listOf("pechuga", "muslos de pollo")),
        Ingredient("carne_molida", Text("Carne molida", "Ground beef"), listOf("carne picada", "molida")),
        Ingredient("salchicha", Text("Salchichas", "Sausages"), listOf("vienesas", "hot dog")),
        Ingredient("jamon", Text("Jamón", "Ham")),
        Ingredient("atun", Text("Atún", "Tuna"), listOf("atun en lata")),
        Ingredient("caraotas", Text("Caraotas", "Beans"), listOf("frijoles", "porotos", "habichuelas", "frejoles", "black beans")),
        Ingredient("lentejas", Text("Lentejas", "Lentils")),
        Ingredient("papa", Text("Papas", "Potatoes"), listOf("patata", "potato")),
        Ingredient("platano", Text("Plátano", "Plantain"), listOf("platano verde", "platano maduro")),
        Ingredient("yuca", Text("Yuca", "Cassava"), listOf("mandioca", "casava")),
        Ingredient("zanahoria", Text("Zanahoria", "Carrot")),
        Ingredient("aguacate", Text("Aguacate", "Avocado"), listOf("palta")),
        Ingredient("limon", Text("Limón", "Lime"), listOf("lima", "lemon")),
        Ingredient("cilantro", Text("Cilantro", "Cilantro"), listOf("culantro", "coriander")),
        Ingredient("avena", Text("Avena", "Oats"), listOf("oatmeal")),
        Ingredient("banana", Text("Cambur", "Banana"), listOf("guineo", "banano", "platano de seda")),
        Ingredient("arvejas", Text("Arvejas", "Peas"), listOf("guisantes", "chicharos", "petit pois")),
        Ingredient("maiz", Text("Maíz", "Corn"), listOf("jojoto", "choclo", "elote", "maiz dulce")),
        Ingredient("sal", Text("Sal", "Salt"), staple = true),
        Ingredient("aceite", Text("Aceite", "Oil"), staple = true),
        Ingredient("agua", Text("Agua", "Water"), staple = true),
        Ingredient("pimienta", Text("Pimienta", "Black pepper"), staple = true),
        Ingredient("azucar", Text("Azúcar", "Sugar"), listOf("papelon", "panela"), staple = true),
    )

    private val byId: Map<String, Ingredient> = all.associateBy { it.id }

    /** Cada nombre conocido, ya normalizado, apuntando a su ingrediente. */
    private val byName: Map<String, Ingredient> = buildMap {
        for (ingredient in all) {
            val names = listOf(ingredient.id.replace('_', ' '), ingredient.name.es, ingredient.name.en) + ingredient.aliases
            for (name in names) putIfAbsent(normalize(name), ingredient)
        }
    }

    fun byId(id: String): Ingredient? = byId[id]

    fun isStaple(id: String): Boolean = byId[id]?.staple == true

    /** El ingrediente que corresponde a lo que alguien escribió, aceptando plurales y sin acentos. */
    fun find(raw: String): Ingredient? {
        val key = normalize(raw)
        if (key.isEmpty()) return null
        return singularForms(key).firstNotNullOfOrNull { byName[it] }
    }

    /** Sugerencias mientras se escribe: primero los que empiezan igual, luego los que lo contienen. */
    fun suggest(raw: String, limit: Int = 6): List<Ingredient> {
        val key = normalize(raw)
        if (key.isEmpty()) return emptyList()
        val starts = LinkedHashSet<Ingredient>()
        val contains = LinkedHashSet<Ingredient>()
        for ((name, ingredient) in byName) {
            when {
                name.startsWith(key) -> starts += ingredient
                name.contains(key) -> contains += ingredient
            }
        }
        return (starts + contains).filterNot { it.staple }.take(limit)
    }

    private fun singularForms(key: String): List<String> = buildList {
        add(key)
        if (key.endsWith("es")) add(key.dropLast(2))
        if (key.endsWith("s")) add(key.dropLast(1))
    }
}
