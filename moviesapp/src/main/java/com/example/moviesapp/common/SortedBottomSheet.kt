package com.example.moviesapp.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.moviesapp.R
import com.example.moviesapp.fragments.list.FragmentMoviesList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortedBottomSheet(fragment : FragmentMoviesList) : BottomSheetDialogFragment() {

    interface OnBindSorted{
        fun bind(value : String)
    }

    private var onBindSorted : OnBindSorted? = fragment

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

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sorted_diallog, container, false)

        view.findViewById<TextView>(R.id.now_playing_text).setOnClickListener {
            bindText(text=getString(R.string.now_playong))
        }

        view.findViewById<TextView>(R.id.popular_text).setOnClickListener {
            bindText(text=getString(R.string.popular))
        }

        view.findViewById<TextView>(R.id.top_rated_text).setOnClickListener {
            bindText(text=getString(R.string.top_rated_text))
        }

        view.findViewById<TextView>(R.id.upcoming_text).setOnClickListener {
            bindText(text=getString(R.string.up_coming))
        }

        return view
    }

    private fun bindText(text : String){
        onBindSorted?.bind(value = text)
        dialog?.cancel()
    }
}