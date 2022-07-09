package com.chslcompany.spaceflightnews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.chslcompany.spaceflightnews.core.NetworkState
import com.chslcompany.spaceflightnews.core.NetworkUtil
import com.chslcompany.spaceflightnews.core.PostState
import com.chslcompany.spaceflightnews.databinding.HomeFragmentBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private val adapter by lazy { PostListAdapter() }

    private lateinit var networkUtil: NetworkUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        initRecyclerView()
        observeSnackBarLiveData()
        observePostStateLiveData()
        setupNetwork()
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    private fun setupNetwork() {
        networkUtil = NetworkUtil(requireContext())
        lifecycle.addObserver(networkUtil)

        networkUtil.networkAvailableStateFlow.asLiveData()
            .observe(this, { networkState ->
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