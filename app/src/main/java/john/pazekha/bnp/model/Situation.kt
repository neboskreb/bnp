package john.pazekha.bnp.model

data class Situation(private val matrix: Array<Array<SYMBOL>>) {
    fun getCell(row:Int, col:Int): SYMBOL {
        return matrix[row][col]
    }
}