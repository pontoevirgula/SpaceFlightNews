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
import com.chslcompany.spaceflightnews.databinding.FragmentNewsBinding
import com.chslcompany.spaceflightnews.ui.adapter.PostListAdapter

class NewsFragment : BaseFragment() {

    private val binding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        PostListAdapter { post ->
            findNavController().navigate(
                R.id.action_newsFragment_to_detailFragment,
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
        viewModel.fetchPosts(Search(CategoryEnum.ARTICLES.value))
        initRecyclerView(binding.recyclerNews, adapter)
        observeSnackBarLiveData(binding)
        observePostStateLiveData(adapter)
        return binding.root
    }
}