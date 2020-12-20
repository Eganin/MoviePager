package com.example.moviesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.adapters.ActorsAdapter
import com.example.moviesapp.data.binding.MoviesDetails
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.databinding.FragmentMoviesDetailsBinding
import com.example.moviesapp.utils.imageOption


class FragmentMoviesDetails : Fragment() {


    private val adapter = ActorsAdapter()
    private val movie: Movie by lazy { arguments?.get(SAVE_MOVIE_DATA_KEY) as Movie }
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movies_details, container, false)

        binding.data =
            MoviesDetails(
                ageRating = "+${movie.minimumAge}",
                titleMovie = movie.title,
                tagLine = movie.genres.joinToString(separator = " , ") { it.name },
                reviewsCount = "${movie.numberOfRatings} reviews",
                storyLine = movie.overview)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view = view)
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
        downloadPoster(detailPoster = detailPoster)
        bindStars(view = view, countRating = (movie.ratings / 2).toInt())

    }

    private fun bindStars(view: View, countRating: Int) {
        val listStarsRating = listOf<AppCompatImageView>(
            view.findViewById(R.id.star_one),
            view.findViewById(R.id.star_two),
            view.findViewById(R.id.star_three),
            view.findViewById(R.id.star_four),
            view.findViewById(R.id.star_five)
        )
        for (i in 0 until countRating) {
            listStarsRating[i].setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_star_positive
                )
            )
        }
    }

    private fun downloadPoster(detailPoster: AppCompatImageView) {
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