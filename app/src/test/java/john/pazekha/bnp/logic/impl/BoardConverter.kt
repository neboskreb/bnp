package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import junitparams.converters.ConversionFailedException
import junitparams.converters.Converter
import junitparams.converters.Param


class BoardConverter: Converter<Param, Array<Array<SYMBOL>>> {
    override fun convert(param: Any?): Array<Array<SYMBOL>> {
        var line = param!! as String
        line = line.replace(" ", "")
        if (line.length != 9) {
            throw  ConversionFailedException("Expected 9 symbols separated by spaces but found this: $param")
        }

        val result = Array(9) {SYMBOL.BLANK}
        for ((pos, c) in line.toCharArray().withIndex()) {
            result[pos] = toSymbol(c)
        }

        return asBoard(result)
    }

    private fun toSymbol(c: Char): SYMBOL {
        return when (c) {
            'X' -> SYMBOL.CROSS
            'O' -> SYMBOL.DONUT
            '.' -> SYMBOL.BLANK
            else -> throw IllegalArgumentException("Expected 'X' or 'O' or '.' but found this: [$c]")
        }
    }

    private fun asBoard(cells: Array<SYMBOL>): Array<Array<SYMBOL>> {
        if (cells.size != 9) throw IllegalArgumentException()

        return arrayOf(
            arrayOf(cells[0], cells[1], cells[2]),
            arrayOf(cells[3], cells[4], cells[5]),
            arrayOf(cells[6], cells[7], cells[8]))
    }

    override fun initialize(annotation: Param?) {
        // do nothing
    }

}
