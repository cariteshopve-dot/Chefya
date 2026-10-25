package agency.lacosta.chefya.domain

/** Un texto en los dos idiomas de la app. Todo lo que no sea español se muestra en inglés. */
data class Text(val es: String, val en: String) {
    fun get(language: String): String = if (language == "es") es else en
}

/** Minúsculas, sin acentos y sin espacios de más, para comparar lo que escribe la gente. */
fun normalize(raw: String): String =
    java.text.Normalizer.normalize(raw.lowercase().trim(), java.text.Normalizer.Form.NFD)
        .replace(Regex("\\p{M}+"), "")
        .replace(Regex("\\s+"), " ")
