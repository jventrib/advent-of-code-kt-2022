import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

val day13 = day(13) {
    part1(expectedExampleOutput = 13, expectedOutput = 6240) {
        val pairs = input.chunked(3).map { Pair(it[0], it[1]) }
        val filter = pairs.mapIndexed { i, pair -> i to testOrder(pair) }.filter { it.second }.map { it.first + 1 }
        filter.sum()
    }

    part2(expectedExampleOutput = 140, expectedOutput = 23142) {

        val list = input.filter { it.isNotEmpty() } + "[[2]]" + "[[6]]"
        val sortedPacket = list.map(Json.Default::parseToJsonElement).sortedWith(JsonElement::compareTo)
        val index2 = sortedPacket.indexOfFirst { Json.encodeToString(it) == "[[2]]" } + 1
        val index6 = sortedPacket.indexOfFirst { Json.encodeToString(it) == "[[6]]" } + 1
        index2 * index6
    }
}

private fun testOrder(pair: Pair<String, String>): Boolean {
    val left = Json.parseToJsonElement(pair.first)
    val right = Json.parseToJsonElement(pair.second)
    return left.compareTo(right) < 0
}

private fun JsonElement.compareTo(other: JsonElement): Int = when {
    this is JsonPrimitive && other is JsonPrimitive -> content.toInt().compareTo(other.content.toInt())
    this is JsonPrimitive && other is JsonArray -> JsonArray(listOf(JsonPrimitive(content))).compareTo(other)
    this is JsonArray && other is JsonPrimitive -> compareTo(JsonArray(listOf(JsonPrimitive(other.content))))

    this is JsonArray && other is JsonArray -> {
        val left = this + List((other.size - size).coerceAtLeast(0)) { JsonPrimitive(-100) }
        val right = other + List((size - other.size).coerceAtLeast(0)) { JsonPrimitive(-100) }
        val zip = left.zip(right)
        zip.fold(0) { acc, pair ->
            if (acc != 0) acc else {
                acc + pair.first.compareTo(pair.second)
            }
        }
    }
    else -> throw Exception("not handled")
}

