package com.example.moviesapp.ui.presentation.movies.view.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.databinding.FragmentMoviesDetailsBinding
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.presentation.movies.utils.imageOptionMovie
import com.example.moviesapp.presentation.movies.utils.network.hasConnection
import com.example.moviesapp.ui.presentation.movies.viewmodel.MoviesDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentMoviesDetails : Fragment() {

    private lateinit var viewModel: MoviesDetailsViewModel
    private var configuration: Images? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var adapter: ActorsAdapter

    private var _binding : FragmentMoviesDetailsBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesDetailsViewModel(
                fragment = this
            )
        if (hasConnection(context = requireContext())) {
            arguments?.getLong(SAVE_MOVIE_DATA_KEY)?.let { viewModel.loadDetailData(id = it) }
        } else {
            arguments?.getLong(SAVE_MOVIE_DATA_KEY)?.let { viewModel.loadDetailDataFromDB(id = it) }
        }
        viewModel.startLoadingData()
        viewModel.state.observe(viewLifecycleOwner, this::setStateLoading)
        viewModel.info.observe(viewLifecycleOwner, this::bindViews)
        viewModel.credits.observe(viewLifecycleOwner) {
            adapter.bindActors(newActors = it.cast)
            adapter.notifyDataSetChanged()
        }

        setupRecyclerView()
        binding.backActivity.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.backActivityPath.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.setDate.setOnClickListener {
            scope.launch {
                arguments?.getLong(SAVE_MOVIE_DATA_KEY)?.let {
                    val data = (requireActivity().application as MovieApp)
                        .myComponent.getMovieRepository().getDetailMovieById(id = it)

                    CalendarView(movie=data)
                        .show(this@FragmentMoviesDetails.parentFragmentManager, "CALENDAR")
                }
            }
        }

        viewModel.stopLoadingData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val config = arguments?.get(SAVE_CONFIGURATION)
        config?.let { configuration = it as Images }
        adapter = if (!hasConnection(context = requireContext())) {
            configuration = null
            ActorsAdapter()
        } else {
            ActorsAdapter(configuration = configuration)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun setStateLoading(state: Boolean) {
        binding.progressBarDetails.isVisible = state
    }


    private fun setupRecyclerView() {
        binding.actorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.actorsRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews(data: ResponseMovieDetail) {
        val localize = Locale.getDefault().language
        binding.pg.text = if (data.adult == true) "+18" else "+16"
        binding.titleMovie.text = data.title
        val genres = data.genres?.filter { it != null }
        binding.tagLine.text = genres?.joinToString(separator = " , ") { it.name ?: "" }
        binding.reviewsCount.text =
            if (localize == "english") "${data.voteCount} reviews" else "${data.voteCount} обзоров"
        binding.storylineDescription.text = data.overview
        downloadPoster( data = data)
        bindStars(countRating = (data.voteAverage?.div(2))?.toInt() ?: 0)


    }

    private fun bindStars(countRating: Int) {
        val listStarsRating = listOf(
            binding.starOne as AppCompatImageView,
            binding.starTwo as AppCompatImageView,
            binding.starThree as AppCompatImageView,
            binding.starFour as AppCompatImageView,
            binding.starFive as AppCompatImageView,
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

    private fun downloadPoster( data: ResponseMovieDetail) {
        if (binding.detailPoster != null) {
            Glide.with(requireContext())
                .clear(binding.detailPoster)

            Glide.with(requireContext())
                .load(configuration?.baseURL + (configuration?.backdropSizes?.get(3)) + data.backdropPath)
                .apply(imageOptionMovie)
                .into(binding.detailPoster)
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