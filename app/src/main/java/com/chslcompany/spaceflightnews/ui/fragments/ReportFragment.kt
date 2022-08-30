package com.chslcompany.spaceflightnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chslcompany.spaceflightnews.R
import com.chslcompany.spaceflightnews.core.BaseFragment
import com.chslcompany.spaceflightnews.core.CategoryEnum
import com.chslcompany.spaceflightnews.core.POST_KEY
import com.chslcompany.spaceflightnews.data.model.Search
import com.chslcompany.spaceflightnews.databinding.FragmentReportBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter

class ReportFragment : BaseFragment() {

    private val binding: FragmentReportBinding by lazy {
        FragmentReportBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        PostListAdapter { post ->
            findNavController().navigate(
                R.id.action_reportFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(POST_KEY, post)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding(binding,viewLifecycleOwner)
        viewModel.fetchPosts(Search(CategoryEnum.BLOGS.value))
        initRecyclerView(binding.recyclerReport, adapter)
        observeSnackBarLiveData(binding)
        observePostStateLiveData(adapter)
        return binding.root
    }
}