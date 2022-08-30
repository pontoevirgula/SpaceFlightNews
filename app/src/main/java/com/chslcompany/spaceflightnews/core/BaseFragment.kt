package com.chslcompany.spaceflightnews.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.spaceflightnews.databinding.FragmentBlogsBinding
import com.chslcompany.spaceflightnews.databinding.FragmentNewsBinding
import com.chslcompany.spaceflightnews.databinding.FragmentReportBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter
import com.chslcompany.spaceflightnews.ui.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment : Fragment() {

    val viewModel: SharedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupNetwork()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun initRecyclerView(recyclerView: RecyclerView, postListAdapter: PostListAdapter) {
        recyclerView.adapter = postListAdapter
    }

    fun initBinding(
        binding: ViewDataBinding,
        viewLifecycleOwner: LifecycleOwner
    ) {
        when (binding) {
            is FragmentNewsBinding -> {
                initNewsBinding(binding, viewLifecycleOwner)
            }
            is FragmentBlogsBinding -> {
                initBlogBinding(binding, viewLifecycleOwner)
            }
            is FragmentReportBinding -> {
                initReportBinding(binding, viewLifecycleOwner)
            }
        }
    }

    private fun initNewsBinding(
        binding: FragmentNewsBinding,
        viewLifecycleOwner: LifecycleOwner
    ) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initReportBinding(
        binding: FragmentReportBinding,
        viewLifecycleOwner: LifecycleOwner
    ) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initBlogBinding(
        binding: FragmentBlogsBinding,
        viewLifecycleOwner: LifecycleOwner
    ) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupNetwork() {
        val networkUtil = NetworkUtil(requireContext())
        lifecycle.addObserver(networkUtil)

        networkUtil.networkAvailableStateFlow.asLiveData()
            .observe(viewLifecycleOwner, { networkState ->
                handleNetworkState(networkState)
            })
    }

    private fun handleNetworkState(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.Available -> Log.i(CONNECTED, "Internet OK")
            else -> Log.i(DISCONNECTED, "Sem internet")
        }
    }

    fun observePostStateLiveData(adapter: PostListAdapter) =
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

    fun observeSnackBarLiveData(binding: ViewDataBinding) =
        viewModel.snackBar.observe(viewLifecycleOwner) { messageError ->
            if (!messageError.isNullOrEmpty()) {
                Snackbar.make(
                    bindingRoot(binding),
                    messageError,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.hideSnackBar()
            }
        }

    private fun bindingRoot(binding: ViewDataBinding): View {
        try {
            if (binding is FragmentNewsBinding ||
                binding is FragmentBlogsBinding ||
                binding is FragmentReportBinding
            ) {
                return binding.root
            }
        } catch (e: Exception) {
            Log.e(" Illegal binding", e.toString())
        }
        throw Exception()
    }

    companion object {
        private const val CONNECTED = "CONNECTED"
        private const val DISCONNECTED = "DISCONNECTED"
    }
}
