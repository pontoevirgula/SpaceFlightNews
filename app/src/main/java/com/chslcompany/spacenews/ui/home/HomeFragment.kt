package com.chslcompany.spacenews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.chslcompany.spacenews.R
import com.chslcompany.spacenews.core.CategoryEnum
import com.chslcompany.spacenews.core.NetworkState
import com.chslcompany.spacenews.core.NetworkUtil
import com.chslcompany.spacenews.core.PostState
import com.chslcompany.spacenews.data.entities.model.Search
import com.chslcompany.spacenews.databinding.HomeFragmentBinding
import com.chslcompany.spacenews.ui.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private val adapter by lazy { PostListAdapter() }

    private lateinit var networkUtil: NetworkUtil

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        initRecyclerView()
        initOptionsMenu()
        initSearchView()
        observeSnackBarLiveData()
        observePostStateLiveData()
        setupNetwork()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCategoryLiveData()
    }

    private fun initSearchView() {
        with(binding.homeToolbar) {
            val searchItem = menu.findItem(R.id.search_menu)
            searchView = searchItem.actionView as SearchView
            searchView.isIconified = false
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val searchString = searchView.query.toString()
                    viewModel.searchPostsTitleContains(searchString)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { text->
                        viewModel.searchPostsTitleContains(text)
                    }
                    return true
                }

            })
        }
    }

    private fun observeCategoryLiveData() =
        viewModel.category.observe(viewLifecycleOwner){
            searchView.queryHint = "${getString(R.string.search_in)} " + when (it) {
                CategoryEnum.ARTICLES -> getString(R.string.news)
                CategoryEnum.BLOGS -> getString(R.string.blogs)
                CategoryEnum.REPORTS -> getString(R.string.reports)
                else -> getString(R.string.latest_news)
            }
        }

    private fun initOptionsMenu() {
        with(binding.homeToolbar) {
            inflateMenu(R.menu.options_menu)

            menu.findItem(R.id.articles_menu).setOnMenuItemClickListener {
                viewModel.fetchPosts(Search(CategoryEnum.ARTICLES.value))
                true
            }
            menu.findItem(R.id.blogs_menu).setOnMenuItemClickListener {
                viewModel.fetchPosts(Search(CategoryEnum.BLOGS.value))
                true
            }
            menu.findItem(R.id.report_menu).setOnMenuItemClickListener {
                viewModel.fetchPosts(Search(CategoryEnum.REPORTS.value))
                true
            }
        }
    }

    private fun setupNetwork() {
        networkUtil = NetworkUtil(requireContext())
        lifecycle.addObserver(networkUtil)

        networkUtil.networkAvailableStateFlow.asLiveData()
            .observe(viewLifecycleOwner) { networkState ->
                handleNetworkState(networkState)
            }
    }

    private fun handleNetworkState(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.Available -> Log.i("Teste", "conectado")
            else -> Log.i("Teste", "desconectado")
        }
    }

    private fun initRecyclerView() {
        binding.homeRv.adapter = adapter
    }

    private fun observePostStateLiveData() =
        viewModel.listPost.observe(viewLifecycleOwner) { state ->
            when (state) {
                PostState.Loading -> {
                    viewModel.showProgressBar()
                }
                is PostState.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(state.result)
                }
                is PostState.Error -> {
                    viewModel.hideProgressBar()
                    Log.e("Error", state.throwable.message.toString())
                }
            }
        }

    private fun observeSnackBarLiveData() =
        viewModel.snackBar.observe(viewLifecycleOwner) { messageError ->
            if (!messageError.isNullOrEmpty()) {
                Snackbar.make(
                    binding.root,
                    messageError,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.hideSnackBar()
            }
        }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}