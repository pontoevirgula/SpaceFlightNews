package com.chslcompany.spaceflightnews.domain.usecase

import com.chslcompany.spaceflightnews.core.BaseUseCase
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.data.model.Search
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPostTitleContainsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, List<Post>>() {

    override suspend fun execute(param: Search): Flow<List<Post>> = flow {
        repository.listPostTitleContains(
            category = param.type,
            titleContains = param.titleSearch
        )
    }


}