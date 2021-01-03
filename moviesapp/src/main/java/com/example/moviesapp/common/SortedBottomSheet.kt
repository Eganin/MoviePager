package com.example.moviesapp.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.moviesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortedBottomSheet : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView =
            View.inflate(context, R.layout.sorted_diallog, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            ContextCompat.getColor(
                contentView.context,
                android.R.color.transparent
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sorted_diallog, container, false)

        view.findViewById<TextView>(R.id.now_playing_text).setOnClickListener {

        }

        view.findViewById<TextView>(R.id.popular_text).setOnClickListener {

        }

        view.findViewById<TextView>(R.id.top_rated_text).setOnClickListener {

        }

        view.findViewById<TextView>(R.id.upcoming_text).setOnClickListener {

        }

        return view
    }
}