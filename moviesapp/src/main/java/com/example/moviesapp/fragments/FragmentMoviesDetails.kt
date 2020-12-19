package com.example.moviesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.adapters.ActorsAdapter
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.utils.imageOption

class FragmentMoviesDetails private constructor() : Fragment() {


    private val adapter = ActorsAdapter()
    private val movie: Movie by lazy { arguments?.get(SAVE_MOVIE_DATA_KEY) as Movie }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view=view)
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
        adapter.bindActors(newActors = movie.actors)
        adapter.notifyDataSetChanged()
    }

    private fun bindViews(view: View) {
        val detailPoster = view.findViewById<AppCompatImageView>(R.id.detail_poster)
        val ageRating = view.findViewById<AppCompatTextView>(R.id.pg)
        val titleMovie = view.findViewById<AppCompatTextView>(R.id.title_movie)
        val tagLine = view.findViewById<AppCompatTextView>(R.id.tag_line)
        val reviewsCount = view.findViewById<AppCompatTextView>(R.id.reviews_count)
        val storyLine = view.findViewById<AppCompatTextView>(R.id.storyline_description)

        ageRating.text = "+${movie.minimumAge}"
        titleMovie.text = movie.title
        tagLine.text = movie.genres.joinToString(separator = " , ") { it.name }
        reviewsCount.text = "${movie.numberOfRatings} reviews"
        storyLine.text = movie.overview

        Glide.with(requireContext())
            .clear(detailPoster)

        Glide.with(requireContext())
            .load(movie.poster)
            .apply(imageOption)
            .into(detailPoster)
    }

    companion object {
        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putParcelable(SAVE_MOVIE_DATA_KEY, movie)
            fragment.arguments = bundle
            return fragment
        }

        private const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"
    }


}