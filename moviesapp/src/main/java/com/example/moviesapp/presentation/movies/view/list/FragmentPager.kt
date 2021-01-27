package com.example.moviesapp.presentation.movies.view.list

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.presentation.movies.utils.*
import com.example.moviesapp.presentation.movies.utils.network.hasConnection
import com.example.moviesapp.presentation.movies.viewmodel.MovieDataType
import com.example.moviesapp.presentation.movies.viewmodel.MoviesListViewModel
import kotlinx.coroutines.launch

class FragmentPager : Fragment() {

    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var sortedMoviesText: TextView? = null
    private var sortedMoviesImage: ImageView? = null
    private var searchImage: ImageView? = null
    private var searchEditText: EditText? = null
    private var searchText: TextView? = null

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view = view)
        viewModel.moviesList.observe(viewLifecycleOwner, this::updateAdapter)
        viewModel.state.observe(viewLifecycleOwner, this::setStateLoading)

        if (sortedMoviesText?.text == getText(R.string.search_value)) {
            setupSearching()
        } else if (!hasConnection(context = requireContext()) && adapter.size() == 0) {
            when (getTypeMovies()) {
                MovieDataType.POPULAR -> viewModel.popularMovies.observe(
                    viewLifecycleOwner,
                    this::updateAdapter
                )
                MovieDataType.TOP_RATED -> viewModel.topRatedMovies.observe(viewLifecycleOwner) {
                    updateAdapter(data = it.topRatedToResult())
                }
                MovieDataType.NOW_PLAYING -> viewModel.nowPlayongMovies.observe(viewLifecycleOwner) {
                    updateAdapter(data = it.nowPlayongToResult())
                }
                MovieDataType.UP_COMING -> viewModel.upComingMovies.observe(viewLifecycleOwner) {
                    updateAdapter(data = it.upComingToResult())
                }
            }
        } else if (sortedMoviesText?.text != getText(R.string.search_value) && hasConnection(context = requireContext())) {
            when {
                CounterPopular.count == 0 -> viewModel.deleteAllPopularMoviesDB()
                CounterTopRated.count == 0 -> viewModel.deleteAllTopRatedMoviesDB()
                CounterNowPlayong.count == 0 -> viewModel.deleteAllTNowPlayongMoviesDB()
                CounterUpcoming.count == 0 -> viewModel.deleteAllTUpComingMoviesDB()
            }

            bind(
                value = arguments?.getString(SAVE_SORTED_TYPE) ?: "Popular",
                saveInstance = savedInstanceState
            )


        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModel(fragment = this)
        adapter =
            MoviesAdapter(
                viewModel = viewModel,
                type = MovieDataType.POPULAR,
                repository = (requireActivity().application as MovieApp).myComponent.getRepository()
            )
        if (context is MoviesAdapter.OnClickPoster) adapter.onClickPoster = context
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
        recycler = null
        progressBar = null
        sortedMoviesImage = null
        sortedMoviesText = null
        searchImage = null
        searchText = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler?.adapter = null
        recycler = null
    }

    fun bind(value: String, saveInstance: Bundle?) {
        sortedMoviesText?.text = value
        prepareData()
        if (saveInstance == null ) {
            downloadData()
        }
    }

    private fun setupSearching() {
        prepareData()
        CounterSearch.count = 0
        searchImage?.isVisible = true
        searchEditText?.isVisible = true
        progressBar?.isVisible = false
        searchText?.isVisible = true

        searchEditText?.afterTextChanged {
            searchText?.isVisible = false
            if (it.isNotEmpty()) {
                Handler().postDelayed({
                    CounterSearch.count = 0
                    if (hasConnection(context = requireContext())) {
                        lifecycleScope.launch {
                            downloadData()
                        }
                    }
                }, 2000)
            } else {
                searchText?.isVisible = true
                adapter.clearMovies()
            }

        }
    }

    private fun prepareData() {
        val bundle = Bundle()
        bundle.putString(SAVE_SORTED_TYPE, (sortedMoviesText?.text ?: "Popular").toString())
        arguments = bundle
        adapter.type = getTypeMovies()
        adapter.query = searchEditText?.text.toString().trim()
    }

    private fun getTypeMovies() = when (sortedMoviesText?.text.toString().trim()) {
        getString(R.string.popular) -> MovieDataType.POPULAR
        getString(R.string.top_rated_text) -> MovieDataType.TOP_RATED
        getString(R.string.now_playong) -> MovieDataType.NOW_PLAYING
        getString(R.string.up_coming) -> MovieDataType.UP_COMING
        else -> MovieDataType.SEARCH

    }


    private fun setStateLoading(state: Boolean) {
        progressBar?.isVisible = state
    }

    private fun downloadData() {
        when (sortedMoviesText?.text) {
            getString(R.string.popular) -> viewModel.loadDataModel(type = MovieDataType.POPULAR)
            getString(R.string.top_rated_text) -> viewModel.loadDataModel(type = MovieDataType.TOP_RATED)
            getString(R.string.now_playong) -> viewModel.loadDataModel(type = MovieDataType.NOW_PLAYING)
            getString(R.string.up_coming) -> viewModel.loadDataModel(type = MovieDataType.UP_COMING)
            getString(R.string.search_value) -> viewModel.loadDataModel(
                type = MovieDataType.SEARCH,
                query = searchEditText?.text.toString().trim()
            )
        }
    }


    private fun setupUI(view: View) {
        recycler = view.findViewById(R.id.movies_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar_movies_list)
        sortedMoviesImage = view.findViewById(R.id.shape_for_movies_label)
        sortedMoviesText = view.findViewById(R.id.movies_list_label)
        searchImage = view.findViewById(R.id.search_image)
        searchEditText = view.findViewById(R.id.search_edit_text)
        searchText = view.findViewById(R.id.search_text)
        setupRecyclerView()

        sortedMoviesText?.text = arguments?.getString(SAVE_SORTED_TYPE) ?: "Popular"

    }

    private fun setupRecyclerView() {
        recycler?.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen()
        )

        recycler?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                    try {
                        searchEditText?.focusable = View.NOT_FOCUSABLE
                    } catch (e: NoSuchMethodError) {
                        e.printStackTrace()
                    }
                }
            }
        })

        recycler?.adapter = adapter
    }

    private fun updateAdapter(data: List<Result>) {
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

    companion object {
        private const val SAVE_SORTED_TYPE = "SAVE_SORTED_TYPE"
        fun newInstance(text: String): FragmentPager {
            val fragment = FragmentPager()
            val bundle = Bundle()
            bundle.putString(SAVE_SORTED_TYPE, text)
            fragment.arguments = bundle

            return fragment
        }
    }
}