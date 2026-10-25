package agency.lacosta.chefya.domain

/**
 * Recetas que vienen dentro de la app: caseras, latinas, de 30 minutos o menos y con lo que
 * suele haber en la cocina. Funcionan sin internet; la foto con IA sumará más después.
 */
object Recipes {

    private fun t(es: String, en: String) = Text(es, en)

    val all: List<Recipe> = listOf(
        Recipe(
            id = "perico",
            name = t("Perico", "Venezuelan scrambled eggs"),
            minutes = 10,
            ingredients = listOf("huevo", "tomate", "cebolla", "aceite", "sal"),
            optional = listOf("pimenton"),
            steps = listOf(
                t("Pica la cebolla y el tomate en cuadritos.", "Dice the onion and tomato."),
                t("Sofríe la cebolla en un poco de aceite hasta que esté transparente.", "Cook the onion in a little oil until soft."),
                t("Agrega el tomate y cocina 2 minutos.", "Add the tomato and cook for 2 minutes."),
                t("Bate los huevos con sal, échalos y revuelve hasta que cuajen.", "Beat the eggs with salt, pour them in and stir until set."),
            ),
        ),
        Recipe(
            id = "arepas",
            name = t("Arepas", "Arepas"),
            minutes = 20,
            ingredients = listOf("harina_maiz", "agua", "sal"),
            optional = listOf("queso", "mantequilla"),
            steps = listOf(
                t("Mezcla 2 tazas de agua tibia con sal y ve agregando la harina hasta tener una masa suave.", "Mix 2 cups of warm water with salt and add the flour until you get a soft dough."),
                t("Deja reposar 2 minutos y forma bolitas; aplánalas.", "Rest 2 minutes, shape into balls and flatten them."),
                t("Cocina en un budare o sartén a fuego medio, 5 a 6 minutos por lado.", "Cook on a griddle or pan over medium heat, 5 to 6 minutes per side."),
                t("Ábrelas y rellena con lo que tengas.", "Split them open and fill with whatever you have."),
            ),
        ),
        Recipe(
            id = "arroz_frito",
            name = t("Arroz frito casero", "Homemade fried rice"),
            minutes = 20,
            ingredients = listOf("arroz", "huevo", "cebolla", "zanahoria", "aceite", "sal"),
            optional = listOf("jamon", "arvejas"),
            steps = listOf(
                t("Usa arroz ya cocido, mejor si es del día anterior.", "Use cooked rice, ideally from the day before."),
                t("Sofríe la cebolla y la zanahoria rallada en aceite.", "Fry the onion and grated carrot in oil."),
                t("Haz a un lado, revuelve ahí los huevos y luego mezcla todo.", "Push aside, scramble the eggs there, then mix everything."),
                t("Agrega el arroz, sal al gusto y saltea 5 minutos a fuego alto.", "Add the rice, salt to taste and stir-fry 5 minutes on high heat."),
            ),
        ),
        Recipe(
            id = "pasta_atun",
            name = t("Pasta con atún", "Tuna pasta"),
            minutes = 20,
            ingredients = listOf("pasta", "atun", "tomate", "ajo", "aceite", "sal"),
            optional = listOf("cebolla", "queso"),
            steps = listOf(
                t("Cocina la pasta en agua con sal.", "Cook the pasta in salted water."),
                t("Mientras, sofríe el ajo picado y agrega el tomate picado; cocina 5 minutos.", "Meanwhile fry the chopped garlic, add the chopped tomato and cook 5 minutes."),
                t("Suma el atún escurrido y calienta.", "Add the drained tuna and heat through."),
                t("Mezcla con la pasta escurrida.", "Toss with the drained pasta."),
            ),
        ),
        Recipe(
            id = "tortilla_papa",
            name = t("Tortilla de papa", "Potato omelette"),
            minutes = 30,
            ingredients = listOf("papa", "huevo", "cebolla", "aceite", "sal"),
            steps = listOf(
                t("Corta las papas en láminas finas y la cebolla en tiras.", "Slice the potatoes thin and the onion into strips."),
                t("Cocínalas en aceite a fuego medio hasta que estén blandas, unos 15 minutos.", "Cook them in oil over medium heat until tender, about 15 minutes."),
                t("Escúrrelas y mézclalas con los huevos batidos y sal.", "Drain and mix with the beaten eggs and salt."),
                t("Cuaja en el sartén 4 minutos por lado; dale la vuelta con un plato.", "Set in the pan 4 minutes per side, flipping it with a plate."),
            ),
        ),
        Recipe(
            id = "quesadillas",
            name = t("Quesadillas", "Quesadillas"),
            minutes = 10,
            ingredients = listOf("tortilla", "queso"),
            optional = listOf("jamon", "pollo", "aguacate"),
            steps = listOf(
                t("Pon queso sobre media tortilla y dóblala.", "Put cheese on half a tortilla and fold it."),
                t("Cocina en un sartén seco hasta que el queso se derrita, 2 minutos por lado.", "Cook in a dry pan until the cheese melts, 2 minutes per side."),
                t("Corta en triángulos y sirve.", "Cut into wedges and serve."),
            ),
        ),
        Recipe(
            id = "arroz_caraotas",
            name = t("Arroz con caraotas", "Rice and beans"),
            minutes = 25,
            ingredients = listOf("arroz", "caraotas", "cebolla", "ajo", "aceite", "sal"),
            optional = listOf("pimenton", "platano"),
            steps = listOf(
                t("Usa caraotas ya cocidas o de lata.", "Use cooked or canned beans."),
                t("Sofríe la cebolla y el ajo picados.", "Fry the chopped onion and garlic."),
                t("Agrega las caraotas con un poco de su caldo y cocina 10 minutos.", "Add the beans with a little of their liquid and simmer 10 minutes."),
                t("Sirve con arroz blanco.", "Serve with white rice."),
            ),
        ),
        Recipe(
            id = "tostones",
            name = t("Tostones", "Tostones"),
            minutes = 20,
            ingredients = listOf("platano", "aceite", "sal"),
            optional = listOf("queso", "ajo"),
            steps = listOf(
                t("Pela el plátano verde y córtalo en ruedas gruesas.", "Peel the green plantain and cut into thick rounds."),
                t("Fríelas 3 minutos, sácalas y aplástalas.", "Fry them 3 minutes, remove and flatten them."),
                t("Fríelas otra vez hasta que estén doradas y ponles sal.", "Fry again until golden and salt them."),
            ),
        ),
        Recipe(
            id = "pollo_guisado",
            name = t("Pollo guisado", "Chicken stew"),
            minutes = 30,
            ingredients = listOf("pollo", "tomate", "cebolla", "ajo", "pimenton", "aceite", "sal"),
            optional = listOf("papa", "zanahoria"),
            steps = listOf(
                t("Corta el pollo en trozos pequeños y sálalo.", "Cut the chicken into small pieces and salt it."),
                t("Dóralo en aceite y sácalo.", "Brown it in oil and set aside."),
                t("En el mismo caldero sofríe cebolla, ajo y pimentón; agrega el tomate.", "In the same pot fry onion, garlic and pepper, then add the tomato."),
                t("Devuelve el pollo con medio vaso de agua y cocina tapado 15 minutos.", "Return the chicken with half a glass of water and simmer covered for 15 minutes."),
            ),
        ),
        Recipe(
            id = "picadillo",
            name = t("Picadillo", "Picadillo"),
            minutes = 30,
            ingredients = listOf("carne_molida", "tomate", "cebolla", "papa", "aceite", "sal"),
            optional = listOf("zanahoria", "arvejas", "arroz"),
            steps = listOf(
                t("Pica la papa en cuadritos pequeños.", "Cut the potato into small cubes."),
                t("Sofríe la cebolla, agrega la carne y cocina hasta que cambie de color.", "Fry the onion, add the beef and cook until browned."),
                t("Suma tomate y papa con un poco de agua.", "Add tomato and potato with a little water."),
                t("Cocina tapado 15 minutos, hasta que la papa esté blanda.", "Simmer covered 15 minutes, until the potato is tender."),
            ),
        ),
        Recipe(
            id = "sopa_lentejas",
            name = t("Sopa de lentejas", "Lentil soup"),
            minutes = 30,
            ingredients = listOf("lentejas", "zanahoria", "cebolla", "ajo", "agua", "sal"),
            optional = listOf("papa", "pimenton"),
            steps = listOf(
                t("Lava las lentejas.", "Rinse the lentils."),
                t("Sofríe cebolla y ajo, agrega la zanahoria en cuadritos.", "Fry onion and garlic, add the diced carrot."),
                t("Suma las lentejas y 4 tazas de agua.", "Add the lentils and 4 cups of water."),
                t("Cocina 25 minutos hasta que estén suaves; sala al final.", "Simmer 25 minutes until soft; salt at the end."),
            ),
        ),
        Recipe(
            id = "guacamole",
            name = t("Guacamole", "Guacamole"),
            minutes = 10,
            ingredients = listOf("aguacate", "tomate", "cebolla", "limon", "sal"),
            optional = listOf("cilantro", "tortilla"),
            steps = listOf(
                t("Machaca el aguacate con un tenedor.", "Mash the avocado with a fork."),
                t("Agrega tomate y cebolla bien picados.", "Add finely chopped tomato and onion."),
                t("Ponle jugo de limón y sal; prueba y ajusta.", "Season with lime juice and salt; taste and adjust."),
            ),
        ),
        Recipe(
            id = "huevos_rancheros",
            name = t("Huevos rancheros", "Huevos rancheros"),
            minutes = 15,
            ingredients = listOf("huevo", "tortilla", "tomate", "cebolla", "aceite", "sal"),
            optional = listOf("caraotas", "aguacate", "queso"),
            steps = listOf(
                t("Cocina tomate y cebolla picados hasta hacer una salsa.", "Cook chopped tomato and onion into a sauce."),
                t("Calienta las tortillas en el sartén.", "Warm the tortillas in the pan."),
                t("Fríe los huevos.", "Fry the eggs."),
                t("Pon cada huevo sobre una tortilla y báñalo con la salsa.", "Put each egg on a tortilla and spoon the sauce over."),
            ),
        ),
        Recipe(
            id = "avena_cambur",
            name = t("Avena con cambur", "Banana oatmeal"),
            minutes = 10,
            ingredients = listOf("avena", "leche", "banana"),
            optional = listOf("azucar"),
            steps = listOf(
                t("Calienta 1 taza de leche con media taza de avena.", "Heat 1 cup of milk with half a cup of oats."),
                t("Revuelve 5 minutos a fuego bajo hasta que espese.", "Stir 5 minutes over low heat until thick."),
                t("Sirve con el cambur en ruedas.", "Serve topped with sliced banana."),
            ),
        ),
        Recipe(
            id = "panquecas_cambur",
            name = t("Panquecas de cambur", "Banana pancakes"),
            minutes = 15,
            ingredients = listOf("banana", "huevo", "avena"),
            optional = listOf("leche"),
            steps = listOf(
                t("Machaca un cambur maduro.", "Mash a ripe banana."),
                t("Mézclalo con 2 huevos y media taza de avena.", "Mix with 2 eggs and half a cup of oats."),
                t("Cocina cucharadas de mezcla en un sartén engrasado, 2 minutos por lado.", "Cook spoonfuls in a greased pan, 2 minutes per side."),
            ),
        ),
        Recipe(
            id = "sandwich_plancha",
            name = t("Sándwich de jamón y queso a la plancha", "Grilled ham and cheese"),
            minutes = 10,
            ingredients = listOf("pan", "jamon", "queso"),
            optional = listOf("mantequilla", "tomate"),
            steps = listOf(
                t("Arma el sándwich con jamón y queso.", "Build the sandwich with ham and cheese."),
                t("Unta un poco de mantequilla por fuera si tienes.", "Spread a little butter on the outside if you have it."),
                t("Dóralo en el sartén 2 minutos por lado, apretando.", "Toast in the pan 2 minutes per side, pressing down."),
            ),
        ),
        Recipe(
            id = "salchichas_guisadas",
            name = t("Salchichas guisadas", "Sausages in tomato sauce"),
            minutes = 15,
            ingredients = listOf("salchicha", "tomate", "cebolla", "aceite", "sal"),
            optional = listOf("pimenton", "arroz", "pan"),
            steps = listOf(
                t("Corta las salchichas en ruedas.", "Slice the sausages."),
                t("Sofríe la cebolla y agrega el tomate picado.", "Fry the onion and add the chopped tomato."),
                t("Suma las salchichas y cocina 8 minutos.", "Add the sausages and cook 8 minutes."),
            ),
        ),
        Recipe(
            id = "arroz_pollo",
            name = t("Arroz con pollo rápido", "Quick chicken and rice"),
            minutes = 30,
            ingredients = listOf("arroz", "pollo", "zanahoria", "pimenton", "cebolla", "aceite", "sal"),
            optional = listOf("arvejas", "maiz"),
            steps = listOf(
                t("Corta el pollo en cuadritos y dóralo con sal.", "Dice the chicken and brown it with salt."),
                t("Agrega cebolla, pimentón y zanahoria picados.", "Add chopped onion, pepper and carrot."),
                t("Suma 1 taza de arroz y 2 de agua.", "Add 1 cup of rice and 2 of water."),
                t("Tapa y cocina a fuego bajo 18 minutos.", "Cover and cook on low for 18 minutes."),
            ),
        ),
        Recipe(
            id = "ensalada_atun",
            name = t("Ensalada de atún", "Tuna salad"),
            minutes = 10,
            ingredients = listOf("atun", "tomate", "cebolla", "limon", "sal"),
            optional = listOf("aguacate", "maiz", "papa"),
            steps = listOf(
                t("Pica tomate y cebolla.", "Chop tomato and onion."),
                t("Mezcla con el atún escurrido.", "Mix with the drained tuna."),
                t("Aliña con limón y sal.", "Dress with lime and salt."),
            ),
        ),
        Recipe(
            id = "yuca_mojo",
            name = t("Yuca con mojo", "Cassava with garlic mojo"),
            minutes = 30,
            ingredients = listOf("yuca", "ajo", "limon", "aceite", "sal"),
            optional = listOf("cebolla"),
            steps = listOf(
                t("Pela la yuca, córtala en trozos y hiérvela en agua con sal 20 minutos.", "Peel the cassava, cut it up and boil in salted water for 20 minutes."),
                t("Mientras, calienta aceite con ajo machacado sin que se queme.", "Meanwhile warm oil with crushed garlic without burning it."),
                t("Apaga y agrega jugo de limón.", "Take off the heat and add lime juice."),
                t("Escurre la yuca y báñala con el mojo.", "Drain the cassava and pour the mojo over."),
            ),
        ),
    )

    private val byId = all.associateBy { it.id }

    fun byId(id: String): Recipe? = byId[id]
}
