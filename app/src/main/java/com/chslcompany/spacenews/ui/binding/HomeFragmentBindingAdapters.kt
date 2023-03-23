package com.chslcompany.spacenews.ui.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.chslcompany.spacenews.R
import com.chslcompany.spacenews.core.CategoryEnum
import com.google.android.material.appbar.MaterialToolbar

@BindingAdapter("toolbarTitle")
fun MaterialToolbar.setToolbarTitleFromCategory(category : LiveData<CategoryEnum>?){
    category?.let{
        val stringResource = when(it.value) {
            CategoryEnum.ARTICLES -> R.string.latest_news
            CategoryEnum.BLOGS -> R.string.latest_blogs
            CategoryEnum.REPORTS -> R.string.latest_reports
            else -> R.string.latest_news
        }
        this.title = context.getString(stringResource)
    }
}