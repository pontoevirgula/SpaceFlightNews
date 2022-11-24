package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.db.PostDao
import com.chslcompany.spaceflightnews.data.entities.db.toPostListModel
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.network.PostDTO
import com.chslcompany.spaceflightnews.data.entities.network.toPostDbList
import com.chslcompany.spaceflightnews.data.service.SpaceFlightNewsService
import kotlinx.coroutines.flow.first

class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    override suspend fun listPosts(category: String): Resource<List<Post>> =
        networkBoundResources(category)

//    override suspend fun listPostTitleContains(
//        category: String,
//        titleContains: String?
//    ): Resource<List<Post>> =
//        service.getListPostTitleContains(category,titleContains)

    private suspend fun listPostDTO(category: String): List<PostDTO> =
        service.getListPost(category)

    override suspend fun listPostTitleContains(
        category: String,
        titleContains: String?
    ): List<PostDTO> =
        service.getListPostTitleContains(category,titleContains)


    private suspend fun networkBoundResources(category: String): Resource<List<Post>> {
        var dbData = dao.listPosts().first()
        try {
            with(listPostDTO(category)) {
                if (this.isNotEmpty()) {
                    dao.clearDb()
                    dao.saveAll(this.toPostDbList())
                    dbData = dao.listPosts().first()
                }
            }
        } catch (e: Exception) {
            val failure = RemoteException("Unable to retrieve posts. Displaying cache content")
            return Resource.Failure(data = dbData.toPostListModel(), error = failure)
        }

        return Resource.Success(dbData.toPostListModel())
    }

}



