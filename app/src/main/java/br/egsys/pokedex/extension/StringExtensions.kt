package br.egsys.pokedex.extension

fun firstLetterUpperCase(text: String): String {
    val firstLetter = text.substring(0, 1).toUpperCase()
    val otherLetter = text.substring(1)

    return firstLetter + otherLetter
}
