import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlin.random.Random


fun main() {
    val example = true
    val input = readInput(day14.dayNumber, if (example) "input_example.txt" else "input.txt")
    val points = input.getPoints()
    val lines = input.getPoints()
    val flatten = points.flatten()
    val height = 0..flatten.maxOf { it.y }
    val width = flatten.minOf { it.x }..flatten.maxOf { it.x }
    val world = Rect(
        flatten.minOf { it.x }.toFloat(),
        0f,
        flatten.maxOf { it.x }.toFloat(),
        flatten.maxOf { it.y }.toFloat()
    )
    val paths = lines.map { p ->
        Path().apply {
            moveTo(p.first().x.toFloat(), p.first().y.toFloat())
            p.drop(1).forEach {
                lineTo(it.x.toFloat(), it.y.toFloat())
            }
        }
    }

    val rects = lines.map { it.windowed(2) }.flatten()
        .map { list ->
            list.sortedBy { it.x }.sortedBy { it.y }.let {
                Rect(it.first().toOffset(), it.last().toOffset())
            }
        }

    return application {
        Window(onCloseRequest = ::exitApplication) {
            val sandState = remember { mutableStateListOf(Offset(500f, 0f)) }

            LaunchedEffect(sandState) {
                while (sandState.y < 8) {
                    delay(300)
                    sandState = sandState.copy(y = sandState.y + 1f)
                }

            }
            World(world, rects, anim)
        }
    }
}

@Composable
private fun World(
    world: Rect,
    rects: List<Rect>,
    sand: List<Offset>
) {
    Canvas(Modifier.fillMaxSize()) {
        scale(
            scaleX = size.width / (world.width + 1),
            scaleY = size.height / (world.height + 1),
            Offset.Zero
        ) {
            translate(left = -world.left) {
                rects.forEach { r -> drawRect(Color.Black, r.topLeft, Size(r.size.width + 1, r.size.height + 1)) }
                drawOval(Color.Red, Offset(500f, 0f), Size(1f, 1f))
                sand.forEach {
                    drawOval(Color.Yellow, it, Size(1f, 1f))
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

private fun Point.toOffset(): Offset {
    return Offset(x.toFloat(), y.toFloat())
}
