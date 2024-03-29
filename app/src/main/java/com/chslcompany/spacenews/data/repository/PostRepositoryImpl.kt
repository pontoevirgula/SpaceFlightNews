package com.chslcompany.spacenews.data.repository

import com.chslcompany.spacenews.core.RemoteException
import com.chslcompany.spacenews.core.Resource
import com.chslcompany.spacenews.core.networkBoundResources
import com.chslcompany.spacenews.data.db.PostDao
import com.chslcompany.spacenews.data.entities.db.toPostListModel
import com.chslcompany.spacenews.data.entities.model.Post
import com.chslcompany.spacenews.data.entities.network.PostDTO
import com.chslcompany.spacenews.data.entities.network.toPostDbList
import com.chslcompany.spacenews.data.service.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    private val readFromDatabase = {
        dao.listPosts().map {
            it.sortedByDescending { postDb ->
                postDb.publishedAt
            }.toPostListModel()
        }
    }

    private val cleanDatabaseAndSave : suspend (List<PostDTO>) -> Unit = { listPostDTO ->
        dao.clearDb()
        dao.saveAll(listPostDTO.toPostDbList())
    }

    private val errorToLoadNews = {
        RemoteException("Falha ao atualizar as noticias. Exibindo o conteudo antigo...")
    }

    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> =
        networkBoundResources(
            query = readFromDatabase,
            service = {
                service.getListPost(category)
            },
            saveFetchResult = { listPostDTO ->
                cleanDatabaseAndSave(listPostDTO)
            },
            onError = {
                errorToLoadNews.invoke()
            }
        )

    override suspend fun listPostTitleContains(
        category: String,
        titleContains: String?
    ): Flow<Resource<List<Post>>> =
        networkBoundResources(
            query = readFromDatabase ,
            service = {
                service.getListPostTitleContains(category, titleContains)
            },
            saveFetchResult = { dtoList ->
                cleanDatabaseAndSave(dtoList)
            },
            onError = {
                errorToLoadNews.invoke()
            }
        )
   }



