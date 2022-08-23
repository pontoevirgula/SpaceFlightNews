package com.chslcompany.spaceflightnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.chslcompany.spaceflightnews.R
import com.chslcompany.spaceflightnews.core.*
import com.chslcompany.spaceflightnews.data.model.Search
import com.chslcompany.spaceflightnews.databinding.FragmentBlogsBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter
import com.chslcompany.spaceflightnews.ui.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlogFragment : Fragment() {

    private val viewModel: SharedViewModel by viewModel()
    private val binding: FragmentBlogsBinding by lazy {
        FragmentBlogsBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        PostListAdapter { post ->
            findNavController().navigate(
                R.id.action_blogFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(POST_KEY, post)
                }
            )
        }
    }

    private lateinit var networkUtil: NetworkUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        viewModel.fetchPosts(Search(CategoryEnum.BLOGS.value))
        initRecyclerView()
        observeSnackBarLiveData()
        observePostStateLiveData()
        setupNetwork()
        return binding.root
    }

    private fun setupNetwork() {
        networkUtil = NetworkUtil(requireContext())
        lifecycle.addObserver(networkUtil)

        networkUtil.networkAvailableStateFlow.asLiveData()
            .observe(viewLifecycleOwner, { networkState ->
                handleNetworkState(networkState)
            })
    }

    private fun handleNetworkState(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.Available -> Log.i("Teste", "conectado")
            else -> Log.i("Teste", "desconectado")
        }
    }

    private fun initRecyclerView() {
        binding.dashRv.adapter = adapter
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