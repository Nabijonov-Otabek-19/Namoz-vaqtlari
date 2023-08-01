package uz.nabijonov.otabek.prayertime.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

fun logger(msg: String) {
    Log.d("AAA", msg)
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}