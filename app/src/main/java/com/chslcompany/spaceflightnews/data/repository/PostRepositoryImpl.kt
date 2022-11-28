package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.db.PostDao
import com.chslcompany.spaceflightnews.data.entities.db.toPostListModel
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.network.toPostDbList
import com.chslcompany.spaceflightnews.data.service.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> =
        networkBoundResources(
            query = {
                dao.listPosts().map {
                    it.sortedByDescending { postDb ->
                        postDb.publishedAt
                    }.toPostListModel()
                }
            },
            service = {
                service.getListPost(category)
            },
            saveFetchResult = { listPostDTO ->
                dao.clearDb()
                dao.saveAll(listPostDTO.toPostDbList())
            },
            onError = {
                RemoteException("Falha ao atualizar as noticias. Exibindo o conteudo antigo...")
            }
        )

    override suspend fun listPostTitleContains(
        category: String,
        titleContains: String?
    ): Flow<Resource<List<Post>>> =
        networkBoundResources(
            query = {
                dao.listPosts().map {
                    it.sortedByDescending { postDb ->
                        postDb.publishedAt
                    }.toPostListModel()
                }
            },
            service = {
                service.getListPostTitleContains(category, titleContains)
            },
            saveFetchResult = { dtoList ->
                dao.clearDb()
                dao.saveAll(dtoList.toPostDbList())
            },
            onError = {
                RemoteException("Não foi possível carregar lista")
            }
        )


    private inline fun  <ResultType, RequestType> networkBoundResources(
        crossinline query : () -> Flow<ResultType>,
        crossinline service : suspend () -> RequestType,
        crossinline saveFetchResult : suspend (RequestType) -> Unit,
        crossinline onError : (Throwable) -> Throwable
    ) : Flow<Resource<ResultType>> =
        flow {
            var dbData = query().first()
            try {
                if (service() != EMPTY_LIST) {
                    saveFetchResult(service())
                    dbData = query().first()
                    emit(Resource.Success(dbData))
                } else {
                    emit(
                        Resource.Failure(
                            data = dbData,
                            error = onError(RemoteException("Lista vazia"))
                        )
                    )
                }
            } catch (e: Exception) {
                emit(
                    Resource.Failure(
                        data = dbData,
                        error = onError(e)
                    )
                )
            }
        }

    companion object{
        private const val EMPTY_LIST = ""
    }
}



