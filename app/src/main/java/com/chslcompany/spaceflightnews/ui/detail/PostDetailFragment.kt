package com.chslcompany.spaceflightnews.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.chslcompany.spaceflightnews.R
import com.chslcompany.spaceflightnews.core.DateUtil
import com.chslcompany.spaceflightnews.core.POST_KEY
import com.chslcompany.spaceflightnews.core.openHyperlink
import com.chslcompany.spaceflightnews.core.setOnSingleClickListener
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {

    private val bindingDetail: FragmentPostDetailBinding by lazy {
        FragmentPostDetailBinding.inflate(layoutInflater)
    }
    private lateinit var itemPost: Post

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemPost = arguments?.getParcelable<Post>(POST_KEY) as Post

        populateViews(itemPost)

        return bindingDetail.root
    }

    private fun populateViews(itemPost: Post) {
        Glide.with(requireContext())
            .load(itemPost.imageUrl)
            .placeholder(R.drawable.ic_splash)
            .into(bindingDetail.ivDetails)

        bindingDetail.tvTitleDetail.text = itemPost.title

        bindingDetail.tvSummaryDetail.text = itemPost.summary

        bindingDetail.btnGoToCompleteNews.setOnSingleClickListener {
            openHyperlink(itemPost.url)
        }

        bindingDetail.tvPublishAndUpdate.text = configMessage(
            R.string.published_at,
            DateUtil.formatDate(itemPost.publishedAt),
            DateUtil.formatHour(itemPost.publishedAt)
        )
    }

    private fun configMessage(message: Int, date: String, hour: String) =
        HtmlCompat.fromHtml(
            requireContext().getString(message, date, hour),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )


}