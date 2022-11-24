package com.chslcompany.spaceflightnews.domain.usecase

import com.chslcompany.spaceflightnews.core.BaseUseCase
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.model.Search
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetLatestPostsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, Resource<List<Post>>>() {

    override fun execute(param: Search): Flow<Resource<List<Post>>> = flow {
        try {
            emit(repository.listPosts(param.type))
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }
    }

}