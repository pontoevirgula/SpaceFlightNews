package com.chslcompany.spaceflightnews.domain.usecase

import com.chslcompany.spaceflightnews.core.BaseUseCase
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.model.Search
import com.chslcompany.spaceflightnews.data.repository.PostRepository
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