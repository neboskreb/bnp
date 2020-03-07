package john.pazekha.bnp.view.impl

import android.widget.Button
import androidx.databinding.BindingAdapter
import john.pazekha.bnp.model.SYMBOL

class SymbolBindingAdapter {
    companion object {
        @BindingAdapter("symbol")
        @JvmStatic
        fun bind(view: Button, symbol: SYMBOL) {
            view.text = presentation(symbol)
        }

        private fun presentation(symbol: SYMBOL): String {
            return when (symbol) {
                SYMBOL.BLANK -> ""
                SYMBOL.DONUT -> "O"
                SYMBOL.CROSS -> "X"
            }
        }
    }
}