package com.example.moviesapp.presentation.movies.view.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.presentation.movies.utils.imageOptionMovie
import com.example.moviesapp.presentation.movies.viewmodel.MoviesDetailsViewModel


class FragmentMoviesDetails : Fragment(){

    private lateinit var viewModel : MoviesDetailsViewModel
    private val configuration: Images by lazy { arguments?.get(SAVE_CONFIGURATION) as Images }
    private lateinit var adapter: ActorsAdapter
    private var ageRating: AppCompatTextView? = null
    private var titleMovie: AppCompatTextView? = null
    private var tagLine: AppCompatTextView? = null
    private var reviewsCount: AppCompatTextView? = null
    private var storyLine: AppCompatTextView? = null
    private var detailPoster: AppCompatImageView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =  ViewModelProvider(this)[MoviesDetailsViewModel::class.java]
        arguments?.getLong(SAVE_MOVIE_DATA_KEY)?.let { viewModel.loadDetailData(id = it) }
        viewModel.startLoadingData()
        viewModel.state.observe(viewLifecycleOwner, this::setStateLoading)
        viewModel.info.observe(viewLifecycleOwner, { bindViews(view = view, data = it) })
        viewModel.credits.observe(viewLifecycleOwner, {
            adapter.bindActors(newActors = it.cast)
            adapter.notifyDataSetChanged()
        })

        setupViews(view = view)
        setupRecyclerView(view = view)
        view.findViewById<AppCompatTextView>(R.id.back_activity).setOnClickListener {
            activity?.onBackPressed()
        }
        view.findViewById<AppCompatTextView>(R.id.back_activity_path).setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.stopLoadingData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ActorsAdapter(configuration = configuration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailPoster = null
        ageRating = null
        titleMovie = null
        tagLine = null
        reviewsCount = null
        storyLine = null
    }

    private fun setStateLoading(state: Boolean) {
        progressBar?.isVisible = state
    }

    private fun setupViews(view: View) {
        detailPoster = view.findViewById(R.id.detail_poster)
        ageRating = view.findViewById(R.id.pg)
        titleMovie = view.findViewById(R.id.title_movie)
        tagLine = view.findViewById(R.id.tag_line)
        reviewsCount = view.findViewById(R.id.reviews_count)
        storyLine = view.findViewById(R.id.storyline_description)
        progressBar = view.findViewById(R.id.progress_bar_details)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.actors_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews(view: View, data: ResponseMovieDetail) {
        ageRating?.text = if (data.adult == true) "+18" else "+16"
        titleMovie?.text = data.title
        tagLine?.text = data.genres?.joinToString(separator = " , ") { it.name }
        reviewsCount?.text = "${data.voteCount} reviews"
        storyLine?.text = data.overview
        downloadPoster(detailPoster = detailPoster, data = data)
        bindStars(view = view, countRating = (data.voteAverage?.div(2))?.toInt() ?: 0)


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

    private fun downloadPoster(detailPoster: AppCompatImageView?, data: ResponseMovieDetail) {
        if (detailPoster != null) {
            Glide.with(requireContext())
                .clear(detailPoster)

            Glide.with(requireContext())
                .load(configuration.baseURL + (configuration.backdropSizes[3]) + data.backdropPath)
                .apply(imageOptionMovie)
                .into(detailPoster)
        }

    }

    companion object {
        fun newInstance(
            movieId: Long,
            configuration: Images?
        ): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putLong(SAVE_MOVIE_DATA_KEY, movieId)
            bundle.putParcelable(SAVE_CONFIGURATION, configuration)
            fragment.arguments = bundle
            return fragment
        }

        private const val SAVE_MOVIE_DATA_KEY = "SAVE_MOVIE_DATA_KEY"
        private const val SAVE_CONFIGURATION = "SAVE_CONFIGURATION"
    }


}