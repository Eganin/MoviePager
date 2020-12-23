package com.example.moviesapp.fragments.list

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.data.models.Movie
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private val adapter = MoviesAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var viewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view = view)
        viewModel = ViewModelProvider(this@FragmentMoviesList)[MoviesListViewModel::class.java]

        viewModel.moviesList.observe(this@FragmentMoviesList, this::updateAdapter)
        viewModel.state.observe(this@FragmentMoviesList , this::setStateLoading)

        viewModel.loadMoviesModel(context = requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesAdapter.OnClickPoster) adapter.onClickPoster = context
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler?.adapter = null
        recycler = null
        uiScope.cancel()
    }

    fun setStateLoading(state: Boolean) {
        progressBar?.isVisible = state
    }


    private fun setupUI(view: View) {
        recycler = view.findViewById(R.id.movies_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar_movies_list)

        recycler?.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen()
        )

        recycler?.adapter = adapter
    }

    private fun updateAdapter(data: List<Movie>) {
        adapter.bindMovies(newMovies = data)
        adapter.notifyDataSetChanged()
    }

    private fun recalculationScreen(): Int {
        // вычесляем кол-во колонок для GridLayout
        val displayMetrics = DisplayMetrics()

        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }
}