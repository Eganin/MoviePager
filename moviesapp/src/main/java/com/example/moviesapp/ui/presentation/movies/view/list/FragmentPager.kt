package com.example.moviesapp.ui.presentation.movies.view.list

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.example.moviesapp.R
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.databinding.FragmentMoviesListBinding
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.presentation.movies.utils.*
import com.example.moviesapp.presentation.movies.utils.network.hasConnection
import com.example.moviesapp.ui.presentation.movies.viewmodel.MovieDataType
import com.example.moviesapp.ui.presentation.movies.viewmodel.MoviesListViewModel
import kotlinx.coroutines.launch

class FragmentPager : Fragment() {

    private var _binding : FragmentMoviesListBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesListViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.moviesList.observe(viewLifecycleOwner, this::updateAdapter)
        viewModel.state.observe(viewLifecycleOwner, this::setStateLoading)
        viewModel.error.observe(viewLifecycleOwner, this::showToast)

        if (binding.moviesListLabel.text == getText(R.string.search_value)) {
            setupSearching()
        } else if (!hasConnection(context = requireContext()) && adapter.size() == 0) {
            observeValueFromDb()
        } else if (binding.moviesListLabel.text != getText(R.string.search_value) && hasConnection(context = requireContext())) {
            downloadNotDb(savedInstanceState = savedInstanceState)
        }
        val workerRepository =
            (requireActivity().application as MovieApp).myComponent.getWorkerRepository()
        WorkManager.getInstance(requireContext()).enqueue(workerRepository.request)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel =
            (requireActivity().application as MovieApp).myComponent.getMoviesViewModel(fragment = this)
        adapter =
            MoviesAdapter(
                viewModel = viewModel,
                type = MovieDataType.POPULAR,
                repository = (requireActivity().application as MovieApp).myComponent.getMovieRepository()
            )
        if (context is MoviesAdapter.OnClickPoster) adapter.onClickPoster = context
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
        _binding=null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.moviesRecyclerView.adapter = null
    }

    private fun showToast(state: Boolean) {
        if (!state) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeValueFromDb() =
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
            else -> viewModel.upComingMovies.observe(viewLifecycleOwner) {
                updateAdapter(data = it.upComingToResult())
            }
        }

    private fun downloadNotDb(savedInstanceState: Bundle?) {
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


    private fun bind(value: String, saveInstance: Bundle?) {
        binding.moviesListLabel.text = value
        prepareData()
        if (saveInstance == null) {
            downloadData()
        }
    }

    private fun setupSearching() {
        prepareData()
        CounterSearch.count = 0
        binding.searchImage.isVisible = true
        binding.searchEditText.isVisible = true
        binding.progressBarMoviesList.isVisible = false
        binding.searchText.isVisible = true

        binding.searchEditText.afterTextChanged {
            binding.searchText.isVisible = false
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
                binding.searchText.isVisible = true
                adapter.clearMovies()
            }

        }
    }

    private fun prepareData() {
        val bundle = Bundle()
        bundle.putString(SAVE_SORTED_TYPE, (binding.moviesListLabel.text ?: "Popular").toString())
        arguments = bundle
        adapter.type = getTypeMovies()
        adapter.query = binding.searchEditText.text.toString().trim()
    }

    private fun getTypeMovies() = when (binding.moviesListLabel.text.toString().trim()) {
        getString(R.string.popular) -> MovieDataType.POPULAR
        getString(R.string.top_rated_text) -> MovieDataType.TOP_RATED
        getString(R.string.now_playong) -> MovieDataType.NOW_PLAYING
        getString(R.string.up_coming) -> MovieDataType.UP_COMING
        else -> MovieDataType.SEARCH

    }


    private fun setStateLoading(state: Boolean) {
        binding.progressBarMoviesList.isVisible = state
    }

    private fun downloadData() {
        when (binding.moviesListLabel.text) {
            getString(R.string.popular) -> viewModel.loadDataModel(type = MovieDataType.POPULAR)
            getString(R.string.top_rated_text) -> viewModel.loadDataModel(type = MovieDataType.TOP_RATED)
            getString(R.string.now_playong) -> viewModel.loadDataModel(type = MovieDataType.NOW_PLAYING)
            getString(R.string.up_coming) -> viewModel.loadDataModel(type = MovieDataType.UP_COMING)
            getString(R.string.search_value) -> viewModel.loadDataModel(
                type = MovieDataType.SEARCH,
                query = binding.searchEditText.text.toString().trim()
            )
        }
    }


    private fun setupUI() {
        setupRecyclerView()
        binding.moviesListLabel.text = arguments?.getString(SAVE_SORTED_TYPE) ?: "Popular"
    }

    private fun setupRecyclerView() {
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen()
        )

        binding.moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                    try {
                        binding.searchEditText.focusable = View.NOT_FOCUSABLE
                    } catch (e: NoSuchMethodError) {
                        e.printStackTrace()
                    }
                }
            }
        })

        binding.moviesRecyclerView.adapter = adapter
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