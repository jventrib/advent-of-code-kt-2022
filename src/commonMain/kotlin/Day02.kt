val day02 = day<Int>(2) {

    val matchScores = mapOf(
        ("A X") to 3, // Rock vs Rock -> Draw
        ("A Y") to 6, // Rock vs Paper -> Win
        ("A Z") to 0, // Rock vs Scissors -> Lose
        ("B X") to 0, // Paper vs Rock -> Lose
        ("B Y") to 3, // Paper vs Paper -> Draw
        ("B Z") to 6, // Paper vs Scissors -> Win
        ("C X") to 6, // Scissors vs Rock -> Win
        ("C Y") to 0, // Scissors vs Paper -> Lose
        ("C Z") to 3, // Scissors vs Scissors -> Draw
    )

    val myShape = mapOf(
        ("A X") to 3, // Rock and Lose -> Scissors
        ("A Y") to 1, // Rock and Draw -> Rock
        ("A Z") to 2, // Rock and Win -> Paper
        ("B X") to 1, // Paper and Lose -> Rock
        ("B Y") to 2, // Paper and Draw -> Paper
        ("B Z") to 3, // Paper and Win -> Scissors
        ("C X") to 2, // Scissors and Lose -> Paper
        ("C Y") to 3, // Scissors and Draw -> Scissors
        ("C Z") to 1, // Scissors and Win -> Rock
    )

    val shapeScores = mapOf(
        'X' to 1,
        'Y' to 2,
        'Z' to 3,
    )

    val roundScores = mapOf(
        'X' to 0,
        'Y' to 3,
        'Z' to 6,
    )

    part1(expectedExampleOutput = 15, expectedOutput = 15572) {
        input.sumOf { matchScores.getValue(it) + shapeScores.getValue(it[2]) }
    }

    part2(expectedExampleOutput = 12, expectedOutput = 16098) {
        input.sumOf { myShape.getValue(it) + roundScores.getValue(it[2]) }
    }
}
