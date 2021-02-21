package com.example.moviesapp.ui.presentation.movies.view

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    open fun showToast(state: Boolean) {
        if (!state) Toast.makeText(
            requireContext(),
            "An error occurred on the server",
            Toast.LENGTH_SHORT
        ).show()
    }
}