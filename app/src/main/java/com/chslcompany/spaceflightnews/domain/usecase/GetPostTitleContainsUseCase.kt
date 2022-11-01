package com.chslcompany.spaceflightnews.domain.usecase

import com.chslcompany.spaceflightnews.core.BaseUseCase
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.model.Search
import com.chslcompany.spaceflightnews.data.entities.network.toPostListModel
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetPostTitleContainsUseCase(private val repository: PostRepository) :
    BaseUseCase<Search, List<Post>>() {

    override fun execute(param: Search): Flow<List<Post>> = flow {
        try {
            emit(repository.listPostTitleContains(
                category = param.type,
                titleContains = param.titleSearch
            ).toPostListModel())
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }
    }


}