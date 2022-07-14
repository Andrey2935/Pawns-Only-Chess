package chess

import kotlin.math.abs

fun main() {
    val table: Array<Array<String>> = Array(8) { Array(10) { " " } }
    println(
        """
        Pawns-Only Chess
        First Player's name:
    """.trimIndent()
    )
    val first = readln()
    println("Second Player's name:")
    val second = readln()
    fillField(table)
    field(table)
    var x: Int
    var x1: Int
    var y: Int
    var y1: Int
    var countFirst: Int
    var countSecond = 0
     while (true) {
        while (true) {
            countFirst = 0
            println("$first's turn:")
            val input = readln()
            if (input == "exit") {
                println("Bye!")
                return
            }
            y = input[0].code - 96
            y1 = input[2].code - 96
            x = 8 - input[1].digitToInt()
            x1 = 8 - input[3].digitToInt()
            val xx = abs(x1 - x)
            if (x != 6 && xx > 1) println("Invalid Input")
            else if (x == 6 && xx > 2) println("Invalid Input")
            else if (table[x][y] != "W") println("No white pawn at ${input[0]}${input[1]}")
            else if (!input.matches(Regex("[a-h][1-8][a-h][1-8]")) ||
                input[1] >= input[3] || table[x1][y] != " "
            ) println("Invalid Input")
            else if (input[0] == input[2]) {
                table[x1][y1] = "W"
                table[x][y] = " "
                if (xx == 2) countFirst++
                break
            } else if (table[x1][y - 1] == "B") {
                table[x1][y1] = "W"
                table[x][y] = " "
                break
            } else if (table[x1][y + 1] == "B") {
                table[x1][y1] = "W"
                table[x][y] = " "
                break
            } else if (table[3][y - 1] == "B" && countSecond == 1) {
                table[x1][y1] = "W"
                table[x][y] = " "
                table[3][y - 1] = " "
                break
            } else if (table[3][y + 1] == "B" && countSecond == 1) {
                table[x1][y1] = "W"
                table[x][y] = " "
                table[3][y + 1] = " "
                break
            } else println("Invalid Input")

        }
        field(table)
         if (table[0].contains("W")){
             println(
                 """
                         White Wins!
                         Bye!
                     """.trimIndent()
             )
             break
         }
         if (havePawnsApart(table)) return
         if (whiteStalemate(table)) return
         if (blackStalemate(table)) return
        while (true) {
            countSecond = 0
            println("$second's turn:")
            val input = readln()
            if (input == "exit") {
                println("Bye!")
                return
            }
            y = input[0].code - 96
            y1 = input[2].code - 96
            x = 8 - input[1].digitToInt()
            x1 = 8 - input[3].digitToInt()
            val xx = abs(x1 - x)
            if (x != 1 && xx > 1) println("Invalid Input")
            else if (x == 1 && xx > 2) println("Invalid Input")
            else if (table[x][y] != "B") println("No black pawn at ${input[0]}${input[1]}")
            else if (!input.matches(Regex("[a-h][1-8][a-h][1-8]")) ||
                input[1] <= input[3] || table[x1][y] != " "
            ) println("Invalid Input")
            else if (input[0] == input[2]) {
                table[x1][y1] = "B"
                table[x][y] = " "
                if (xx == 2) countSecond++
                break
            } else if (table[x1][y - 1] == "W") {
                table[x1][y1] = "B"
                table[x][y] = " "
                break
            } else if (table[x1][y + 1] == "W") {
                table[x1][y1] = "B"
                table[x][y] = " "
                break
            } else if (table[4][y - 1] == "W" && countFirst == 1) {
                table[x1][y1] = "B"
                table[x][y] = " "
                table[4][y - 1] = " "
                break
            } else if (table[4][y + 1] == "W" && countFirst == 1) {
                table[x1][y1] = "B"
                table[x][y] = " "
                table[4][y + 1] = " "
                break
            } else println("Invalid Input")
        }
        field(table)
         if (table[7].contains("B")){
             println(
                 """
                         Black Wins!
                         Bye!
                     """.trimIndent()
             )
             break
         }
         if (havePawnsApart(table)) return
         if (blackStalemate(table)) return
         if (whiteStalemate(table)) return
    }
}

fun field(table: Array<Array<String>>) {
    for (row in table) {
        println("  +---+---+---+---+---+---+---+---+")
        for ((index, cell) in row.withIndex()) {
            print("$cell | ")
            if (index == 8) break
        }
        println()
    }
    println("  +---+---+---+---+---+---+---+---+")
    println("    a   b   c   d   e   f   g   h")
}

fun fillField(table: Array<Array<String>>) {
    table[1] = arrayOf("", "B", "B", "B", "B", "B", "B", "B", "B") // "", "B", "B", "B", "B", "B", "B", "B", "B"
    table[6] = arrayOf("", "W", "W", "W", "W", "W", "W", "W", "W")
    var x = 8
    for (i in table.indices) {
        table[i][0] = x.toString()
        x--
    }
}
fun havePawnsApart(table: Array<Array<String>>): Boolean {
    var b = 0
    var w = 0
    for (row in table) {
        for (cell in row) {
            if (cell.contains("B")) b++
            if (cell.contains("W")) w++
        }
    }
    if (w == 0) {
        println(
            """
                         Black Wins!
                         Bye!
                     """.trimIndent()
        )
        return true
    }
    if (b == 0) {
        println(
            """
                         White Wins!
                         Bye!
                     """.trimIndent()
        )
        return true
    }
    return false
}
fun whiteStalemate(table: Array<Array<String>>): Boolean {
    var k1 = 0
    for (row in table) {
        for (cell in row){
            if (cell == "W") k1++
        }
    }
    try {
        for ((i, row) in table.withIndex()) {
            for ((j, cell) in row.withIndex()) {
                if (cell.contains("W")) {
                    if ((table[i - 1][j] == "B" && table[i - 1][j - 1] != "B") ||
                        (table[i - 1][j] == "B" && table[i - 1][j + 1] != "B") ||
                        (table[i - 1][j] == "B" && table[i - 1][j - 1] != "B" && table[i - 1][j + 1] != "B")
                    ) k1--
                }
            }
        }
    } catch (_: ArrayIndexOutOfBoundsException) {
    }
    if (k1 == 0) {
        println("""
                 Stalemate!
                 Bye!
             """.trimIndent())
        return true
    }
    return false
}
fun blackStalemate(table: Array<Array<String>>): Boolean {
    var k = 0
    for (row in table) {
        for (cell in row){
            if (cell == "B") k++
        }
    }
    try {
        for ((i, row) in table.withIndex()) {
            for ((j, cell) in row.withIndex()) {
                if (cell.contains("B")) {
                    if ((table[i + 1][j] == "W" && table[i + 1][j - 1] != "W") ||
                        (table[i + 1][j] == "W" && table[i + 1][j + 1] != "W") ||
                        (table[i + 1][j] == "W" && table[i + 1][j - 1] != "W" && table[i + 1][j + 1] != "W")
                    ) k--
                }
            }
        }
    } catch (_: ArrayIndexOutOfBoundsException) {
    }
    if (k == 0) {
        println("""
                 Stalemate!
                 Bye!
             """.trimIndent())
        return true
    }
    return false
}