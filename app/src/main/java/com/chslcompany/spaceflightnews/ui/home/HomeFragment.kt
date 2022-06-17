package com.chslcompany.spaceflightnews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chslcompany.spaceflightnews.databinding.HomeFragmentBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }
    private val adapter by lazy { PostListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        initRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.homeRv.adapter = adapter
    }

    private fun observeViewModel() =
        viewModel.listPost.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}