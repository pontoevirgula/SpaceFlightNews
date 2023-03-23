package com.chslcompany.spacenews.domain.usecase

import com.chslcompany.spacenews.core.BaseUseCase
import com.chslcompany.spacenews.core.RemoteException
import com.chslcompany.spacenews.core.Resource
import com.chslcompany.spacenews.data.entities.model.Post
import com.chslcompany.spacenews.data.entities.model.Search
import com.chslcompany.spacenews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class GetLatestPostsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, Resource<List<Post>>>() {

    override suspend fun execute(param: Search): Flow<Resource<List<Post>>> {
        return try {
            repository.listPosts(param.type)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possível carregar a lista")
        }
    }

}