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

fun main() {
    val example = false
//    val example = true
    val input = readInput(day14.dayNumber, if (example) "input_example.txt" else "input.txt")
    val points = input.getPoints()
    val flatten = points.flatten()
    val world = IntRect(
        flatten.minOf { it.x } - 100,
        0,
        flatten.maxOf { it.x } + 100,
        flatten.maxOf { it.y } + 2
    )
    val rects = points.map { it.windowed(2) }.flatten()
        .map { list ->
            list.sortedBy { it.x }.sortedBy { it.y }.let {
                IntRect(it.first().toIntOffset(), it.last().toIntOffset())
            }
        } + IntRect(-10000, flatten.maxOf { it.y + 2 }, 10000, flatten.maxOf { it.y } + 2)

    return application {
        Window(onCloseRequest = ::exitApplication) {
            var sandGrains = remember { mutableStateListOf(IntOffset(500, 0)) }
            var grainCount by remember { mutableIntStateOf(1) }

            LaunchedEffect(sandGrains) {
                val current = sandGrains.last()
                var i = 0
                while (current.y < 8) {
                    if (i++ % 10000 == 0) delay(1)

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

                        current.y == 0 -> break

                        else -> {
                            grainCount++
                            if (grainCount % 2000 == 0) {
                                println(grainCount)
                                sandGrains.removeIf { g ->
                                    sandGrains.contains(g.copy(y = g.y - 1))
                                            && sandGrains.contains(g.copy(x = g.x - 1, y = g.y - 1))
                                            && sandGrains.contains(g.copy(x = g.x + 1, y = g.y - 1))
                                            && sandGrains.contains(g.copy(x = g.x - 1, y = g.y - 2))
                                            && sandGrains.contains(g.copy(x = g.x + 1, y = g.y - 2))
                                            && sandGrains.contains(g.copy(y = g.y - 2))
                                }
                            }
                            IntOffset(500, 0)
                        }
                    }
                    sandGrains.add(next)

                }
                println(grainCount)

            }
            World(world, rects, sandGrains, grainCount)
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
    sand: List<IntOffset>,
    grainCount: Int
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(Modifier.fillMaxSize()) {
        drawText(textMeasurer, grainCount.toString(), Offset(10f, 10f))
        scale(
            scaleX = size.width / (world.width + 1),
            scaleY = size.height / (world.height + 1),
            Offset.Zero
        ) {
            translate(left = -world.left.toFloat()) {
                rects.forEach { r ->
                    drawRect(
                        Color.Black,
                        r.topLeft.toOffset(),
                        IntSize(r.size.width + 1, r.size.height + 1).toSize()
                    )
                }
                drawOval(Color.Red, Offset(500f, 0f), Size(1f, 1f))
                sand.forEach {
                    drawOval(Color.Yellow, it.toOffset(), Size(1f, 1f))
                }

            }
        }
    }
}

private fun List<String>.getPoints() = this.map { line ->
    line.split(" -> ").map { p ->
        p.split(",").let { Point(it.first().toInt(), it.last().toInt()) }
    }
}

private fun Point.toIntOffset() = IntOffset(x, y)


