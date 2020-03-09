package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL

interface IGameAIHelper {

    fun calculateMoveFor(board: Array<Array<SYMBOL>>, symbol: SYMBOL): Position
}
