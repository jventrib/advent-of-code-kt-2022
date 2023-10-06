import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlin.random.Random


private val <E> List<E>.up: Unit
    get() {}

fun main() {
    val example = false
    val input = readInput(day14.dayNumber, if (example) "input_example.txt" else "input.txt")
    val points = input.getPoints()
    val flatten = points.flatten()
    val height = 0..flatten.maxOf { it.y }
    val width = flatten.minOf { it.x }..flatten.maxOf { it.x }
    val world = IntRect(
        flatten.minOf { it.x },
        0,
        flatten.maxOf { it.x },
        flatten.maxOf { it.y }
    )
    val rects = points.map { it.windowed(2) }.flatten()
        .map { list ->
            list.sortedBy { it.x }.sortedBy { it.y }.let {
                IntRect(it.first().toIntOffset(), it.last().toIntOffset())
            }
        }

    return application {
        Window(onCloseRequest = ::exitApplication) {
            var sandGrains = remember { mutableStateListOf(IntOffset(500, 0)) }

            LaunchedEffect(sandGrains) {
                val current = sandGrains.last()
                while (current.y < 8) {
                    delay(1)

                    val current = sandGrains.last()

                    val down = current.copy(y = current.y + 1)
                    val downLeft = current.copy(x = current.x - 1, y = current.y + 1)
                    val downRight = current.copy(x = current.x + 1, y = current.y + 1)

                    val next = when {
                        !contains(rects, sandGrains, down) -> {
                            sandGrains.removeLast()
                            down
                        }
                        !contains(rects, sandGrains, downLeft) -> {
                            sandGrains.removeLast()
                            downLeft
                        }
                        !contains(rects, sandGrains, downRight) -> {
                            sandGrains.removeLast()
                            downRight
                        }
                        else -> {
                            IntOffset(500, 0)
                        }
                    }
                    sandGrains.add(next)
                }

            }
            World(world, rects, sandGrains)
        }
    }
}

private fun contains(
    rects: List<IntRect>,
    sandGrains: List<IntOffset>,
    grain: IntOffset
) = rects.any { r -> r.copy(right = r.right + 1, bottom = r.bottom + 1).contains(grain) }
        || sandGrains.contains(grain)

@Composable
private fun World(
    world: IntRect,
    rects: List<IntRect>,
    sand: List<IntOffset>
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(Modifier.fillMaxSize()) {
        drawText(textMeasurer, sand.size.toString(), Offset(10f, 10f))
        scale(
            scaleX = size.width / (world.width + 1),
            scaleY = size.height / (world.height + 1),
            Offset.Zero
        ) {
            translate(left = -world.left.toFloat()) {
                rects.forEach { r -> drawRect(Color.Black, r.topLeft.toOffset(), IntSize(r.size.width + 1, r.size.height + 1).toSize()) }
                drawOval(Color.Red, Offset(500f, 0f), Size(1f, 1f))
                sand.forEach {
                    drawOval(Color.Yellow, it.toOffset(), Size(1f, 1f))
                }

            }
        }
    }
}

private fun randomColor() = Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))


private fun List<String>.getPoints() = this.map { line ->
    line.split(" -> ").map { p ->
        p.split(",").let { Point(it.first().toInt(), it.last().toInt()) }
    }
}

private fun Point.toIntOffset() = IntOffset(x, y)


