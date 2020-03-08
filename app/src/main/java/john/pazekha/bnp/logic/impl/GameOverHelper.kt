package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL

class GameOverHelper: IGameOverHelper {
    override fun checkGameOver(board: Array<Array<SYMBOL>>): IGameOverHelper.GameResult {
        return IGameOverHelper.GameResult.gameGoesOn()
    }
}
