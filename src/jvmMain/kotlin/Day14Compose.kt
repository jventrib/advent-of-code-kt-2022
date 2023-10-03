import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() {
    val example = true
    val input = readInput(day14.dayNumber, if (example) "input_example.txt" else "input.txt")
    val points = input.getPoints()
    val height = 0..points.flatten().maxOf { it.y }
    val width = points.flatten().minOf { it.x }..points.flatten().maxOf { it.x }
    val r = Rect(width.first.toFloat(), height.first.toFloat(), width.last.toFloat(), height.last.toFloat())
    return application {


        Window(onCloseRequest = ::exitApplication) {
            var text by remember { mutableStateOf("Hello, World!") }
            Canvas(Modifier.fillMaxSize()) {
                val canvasSize = size.toRect()
                println(size)
                height.forEach { y ->
                    width.forEach { x ->
                        drawRect(Color.Red, Offset(x.toFloat(), y.toFloat()), size / 10f)
                    }
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
