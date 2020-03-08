package john.pazekha.bnp.view.impl

import android.view.View
import androidx.databinding.BindingAdapter

class VisibilityBindingAdapter {
    companion object {
        @BindingAdapter("android:visibility")
        @JvmStatic
        fun bind(view: View, value: Boolean) {
            view.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
}