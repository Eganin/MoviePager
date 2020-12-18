package com.example.moviesapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapters.ActorsAdapter

class FragmentMoviesDetails : Fragment() {


    private val adapter = ActorsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view = view)
        view.findViewById<AppCompatTextView>(R.id.back_activity).setOnClickListener {
            activity?.onBackPressed()
        }
        view.findViewById<AppCompatTextView>(R.id.back_activity_path).setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.actors_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.bindActors(newActors = ActorsDataSource().getActors())
        adapter.notifyDataSetChanged()
    }


}