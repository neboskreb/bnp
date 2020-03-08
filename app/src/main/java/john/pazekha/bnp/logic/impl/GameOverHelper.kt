package john.pazekha.bnp.logic.impl

import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.SYMBOL.*

class GameOverHelper: IGameOverHelper {
    override fun checkGameOver(board: Array<Array<SYMBOL>>): IGameOverHelper.GameResult {
        if (checkRow(CROSS, board, 0) ||
            checkRow(CROSS, board, 1) ||
            checkRow(CROSS, board, 2) ||
            checkColumn(CROSS, board, 0) ||
            checkColumn(CROSS, board, 1) ||
            checkColumn(CROSS, board, 2) ||
            checkDiagonal1(CROSS, board) ||
            checkDiagonal2(CROSS, board))
        {
            return IGameOverHelper.GameResult.gameFinished(CROSS)
        }

        if (checkRow(DONUT, board, 0) ||
            checkRow(DONUT, board, 1) ||
            checkRow(DONUT, board, 2) ||
            checkColumn(DONUT, board, 0) ||
            checkColumn(DONUT, board, 1) ||
            checkColumn(DONUT, board, 2) ||
            checkDiagonal1(DONUT, board) ||
            checkDiagonal2(DONUT, board))
        {
            return IGameOverHelper.GameResult.gameFinished(DONUT)
        }

        if (allCellsFilled(board)) {
            return IGameOverHelper.GameResult.gameFinished(BLANK)
        }

        return IGameOverHelper.GameResult.gameGoesOn()
    }

    private fun allCellsFilled(board: Array<Array<SYMBOL>>): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == BLANK) return false
            }
        }

        return true
    }

    private fun checkColumn(symbol: SYMBOL, board: Array<Array<SYMBOL>>, col: Int): Boolean {
        return board[col][0] == symbol && board[col][1] == symbol && board[col][2] == symbol
    }

    private fun checkRow(symbol: SYMBOL, board: Array<Array<SYMBOL>>, row: Int): Boolean {
        return board[0][row] == symbol && board[1][row] == symbol && board[2][row] == symbol
    }

    private fun checkDiagonal1(symbol: SYMBOL, board: Array<Array<SYMBOL>>): Boolean {
        return board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol
    }

    private fun checkDiagonal2(symbol: SYMBOL, board: Array<Array<SYMBOL>>): Boolean {
        return board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol
    }
}
