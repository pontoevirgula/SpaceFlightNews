package com.chslcompany.spaceflightnews.domain.usecase

import com.chslcompany.spaceflightnews.core.BaseUseCase
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.data.model.Search
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, List<Post>>() {

    override suspend fun execute(param: Search): Flow<List<Post>> =
        repository.listPosts(param.type)

}