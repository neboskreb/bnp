package john.pazekha.bnp.view.impl

import android.widget.TextView
import androidx.databinding.BindingAdapter
import john.pazekha.bnp.R
import john.pazekha.bnp.model.WINNER

class WinnerBindingAdapter {
    companion object {
        @BindingAdapter("winner")
        @JvmStatic
        fun bind(view: TextView, winner: WINNER) {
            view.setText(getResId(winner))
        }

        private fun getResId(winner: WINNER): Int {
            return when (winner) {
                WINNER.DRAW     -> R.string.game_draw
                WINNER.COMPUTER -> R.string.computer_wins
                WINNER.PLAYER   -> R.string.user_wins
            }
        }
    }
}