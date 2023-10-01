import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

val day13 = day(13) {
    part1(expectedExampleOutput = 13, expectedOutput = 0) {
        val pairs = input.chunked(3).map { Pair(it[0], it[1]) }

        val filter =
            pairs.mapIndexed { index, pair -> index to testOrder(pair) }.filter { it.second }.map { it.first + 1 }
        filter.sum()
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}

private fun testOrder(pair: Pair<String, String>): Boolean {
    val left = Json.parseToJsonElement(pair.first)
    val right = Json.parseToJsonElement(pair.second)

    val compareTo = left.compareTo(right)
    println(compareTo)
    return compareTo < 0
}

private fun JsonElement.compareTo(other: JsonElement): Int {
    val i = when {
        this is JsonPrimitive && other is JsonPrimitive -> this.content.compareTo(other.content)
        this is JsonPrimitive && other is JsonArray -> JsonArray(listOf(JsonPrimitive(this.content))).compareTo(other)
        this is JsonArray && other is JsonPrimitive -> this.compareTo(JsonArray(listOf(JsonPrimitive(other.content))))

        this is JsonArray && other is JsonArray -> {
//            if (this.size == 0 || other.size == 0) this.size.compareTo(other.size) else {


            val left = this + List((other.size - this.size).coerceAtLeast(0)) { JsonPrimitive(-100) }
            val right = other + List((this.size - other.size).coerceAtLeast(0)) { JsonPrimitive(-100) }
            val zip = left.zip(right)
            zip.fold(0) { acc, pair ->
                if (acc != 0) acc else {
                    acc + pair.first.compareTo(pair.second)
                }
            }
//            }
        }

        else -> throw Exception("not implemented")
    }
    return i

}

