package com.example.ui.poker.ui

// representacion de los palos (♠️ ♣️ ♥️ ♦️)
enum class Suit(val symbol: Char) {
    SPADES('S'), CLUBS('C'), HEARTS('H'), DIAMONDS('D')
}

// cada carta tiene un valor tipo "A", "K", "2", etc y un palo
data class Card(val value: String, val suit: Suit) {
    val numericValue: Int
        get() = when (value) {
            "A" -> 14
            "K" -> 13
            "Q" -> 12
            "J" -> 11
            "T" -> 10
            else -> value.toIntOrNull() ?: 0
        }

    // esto es para debug, como se vería la carta en texto
    override fun toString(): String = "$value${suit.symbol}"
}

// cada mano de poker tiene 5 cartas
class PokerHand(val cards: List<Card>) {

    fun identifyPlay(): String {
        val sortedValues = cards.map { it.numericValue }.sorted()
        val suits = cards.map { it.suit }.toSet()
        val grouped = cards.groupBy { it.numericValue }.mapValues { it.value.size }
        val counts = grouped.values.sortedDescending()
        val uniqueValues = grouped.keys.sorted()

        val isStraight = uniqueValues.size == 5 && (uniqueValues.last() - uniqueValues.first() == 4)
        val isLowStraight = cards.map { it.numericValue }.toSet() == setOf(14, 2, 3, 4, 5)
        val isFlush = suits.size == 1

        return when {
            (isStraight && isFlush) || (isLowStraight && isFlush) -> "Escalera Color"
            counts == listOf(4, 1) -> "Poker"
            counts == listOf(3, 2) -> "Full"
            isFlush -> "Color"
            isStraight || isLowStraight -> "Escalera"
            counts == listOf(3, 1, 1) -> "Trio"
            counts == listOf(2, 2, 1) -> "Par Doble"
            counts == listOf(2, 1, 1, 1) -> "Par"
            else -> "Carta Alta"
        }
    }

    // esto es para comparar dos manos si tienen la misma jugada
    fun getRankedValues(): List<Int> {
        val grouped = cards.groupBy { it.numericValue }
        return grouped.entries
            .sortedWith(
                compareByDescending<Map.Entry<Int, List<Card>>> { it.value.size }
                    .thenByDescending { it.key }
            )
            .flatMap { entry ->
                List(entry.value.size) { entry.key }
            }
    }

    override fun toString(): String = cards.joinToString(" ")
}

// crea el mazo con las 52 cartas
fun generateDeck(): List<Card> {
    val values = listOf("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A")
    return values.flatMap { value ->
        Suit.values().map { suit -> Card(value, suit) }
    }
}

// reparte dos manos de 5 cartas
fun dealHands(): Pair<PokerHand, PokerHand> {
    val deck = generateDeck().shuffled()
    val hand1 = PokerHand(deck.take(5))
    val hand2 = PokerHand(deck.drop(5).take(5))
    return Pair(hand1, hand2)
}

// compara las dos manos y devuelve quien gana
fun compareHands(hand1: PokerHand, hand2: PokerHand): String {
    val ranking = listOf(
        "Escalera Color", "Poker", "Full", "Color", "Escalera",
        "Trio", "Par Doble", "Par", "Carta Alta"
    )

    val play1 = hand1.identifyPlay()
    val play2 = hand2.identifyPlay()

    if (play1 != play2) {
        val rank1 = ranking.indexOf(play1)
        val rank2 = ranking.indexOf(play2)
        return if (rank1 < rank2) "Gana Mano 1 ($play1)" else "Gana Mano 2 ($play2)"
    }

    // si empatan en jugada, comparamos carta por carta
    val vals1 = hand1.getRankedValues()
    val vals2 = hand2.getRankedValues()

    for (i in vals1.indices) {
        if (vals1[i] > vals2[i]) return "Gana Mano 1 ($play1)"
        if (vals1[i] < vals2[i]) return "Gana Mano 2 ($play2)"
    }

    return "Empate"
}