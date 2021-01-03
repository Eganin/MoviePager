package com.example.moviesapp.fragments.list

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import androidx.fragment.app.viewModels
import com.example.moviesapp.common.SortedBottomSheet
import com.example.moviesapp.common.ViewModelsFactory
import com.example.moviesapp.pojo.movies.popular.Result

class FragmentMoviesList : Fragment(), SortedBottomSheet.OnBindSorted {

    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var sortedMoviesText: TextView? = null
    private var sortedMoviesImage: ImageView? = null
    private var searchImage: ImageView? = null
    private var searchEditText: EditText? = null

    private val viewModel: MoviesListViewModel by viewModels { ViewModelsFactory() }
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
        if (savedInstanceState == null && sortedMoviesText?.text != getText(R.string.search_value) && Counter.count == 0) {
            downloadData()
        }
        if (sortedMoviesText != getText(R.string.search_value)) {
            searchEditText?.text = null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter =
            MoviesAdapter(viewModel = viewModel, type = MovieDataType.POPULAR)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler?.adapter = null
        recycler = null
    }

    override fun bind(value: String) {
        sortedMoviesText?.text = value
        val bundle = Bundle()
        bundle.putString("AAAAAAA", (sortedMoviesText?.text ?: "Popular").toString())
        arguments = bundle
        adapter.clearMovies()
        adapter.type = getTypeMovies()
        adapter.query = searchEditText?.text.toString().trim()
        Counter.count = 0
        downloadData()
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

        sortedMoviesText?.text = arguments?.getString("AAAAAAA") ?: "Popular"

        recycler?.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen()
        )

        recycler?.adapter = adapter

        sortedMoviesText?.setOnClickListener {
            SortedBottomSheet(fragment = this).show(parentFragmentManager, "main_dialog")
        }

        sortedMoviesImage?.setOnClickListener {
            SortedBottomSheet(fragment = this).show(parentFragmentManager, "main_dialog")
        }

        searchImage?.setOnClickListener {
            bind(value = getString(R.string.search_value))
        }

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


}