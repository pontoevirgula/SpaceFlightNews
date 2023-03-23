package com.chslcompany.spacenews.domain.usecase

import com.chslcompany.spacenews.core.BaseUseCase
import com.chslcompany.spacenews.core.RemoteException
import com.chslcompany.spacenews.core.Resource
import com.chslcompany.spacenews.data.entities.model.Post
import com.chslcompany.spacenews.data.entities.model.Search
import com.chslcompany.spacenews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class GetPostTitleContainsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, Resource<List<Post>>>() {

    override suspend fun execute(param: Search): Flow<Resource<List<Post>>> {
        try {
            return repository.listPostTitleContains(
                category = param.type,
                titleContains = param.titleSearch
            )
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }
    }


}