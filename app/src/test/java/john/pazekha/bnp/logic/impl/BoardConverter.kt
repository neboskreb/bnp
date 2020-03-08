package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import junitparams.converters.ConversionFailedException
import junitparams.converters.Converter
import junitparams.converters.Param


class BoardConverter: Converter<Param, Array<SYMBOL>> {
    override fun convert(param: Any?): Array<SYMBOL> {
        var line = param!! as String
        line = line.replace(" ", "")
        if (line.length != 9) {
            throw  ConversionFailedException("Expected 9 symbols separated by spaces but found this: $param")
        }

        val result = Array(9) {SYMBOL.BLANK}
        for ((pos, c) in line.toCharArray().withIndex()) {
            result[pos] = toSymbol(c)
        }

        return result
    }

    private fun toSymbol(c: Char): SYMBOL {
        return when (c) {
            'X' -> SYMBOL.CROSS
            'O' -> SYMBOL.DONUT
            '.' -> SYMBOL.BLANK
            else -> throw IllegalArgumentException("Expected 'X' or 'O' or '.' but found this: [$c]")
        }
    }

    override fun initialize(annotation: Param?) {
        // do nothing
    }

}
