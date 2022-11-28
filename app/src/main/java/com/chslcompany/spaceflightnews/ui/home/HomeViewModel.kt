package com.chslcompany.spaceflightnews.ui.home

import androidx.lifecycle.*
import com.chslcompany.spaceflightnews.core.CategoryEnum
import com.chslcompany.spaceflightnews.core.PostState
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.model.Search
import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
import com.chslcompany.spaceflightnews.domain.usecase.GetPostTitleContainsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val latestPostUseCase: GetLatestPostsUseCase,
    private val searchTitleUseCase: GetPostTitleContainsUseCase
    ) : ViewModel(),
    DefaultLifecycleObserver {

    private val _listPost = MutableLiveData<PostState<List<Post>>>()
    val listPost: LiveData<PostState<List<Post>>>
        get() = _listPost

    private val _progressBarVisible = MutableLiveData(false)
    val progressBar: LiveData<Boolean>
        get() = _progressBarVisible

    private var _snackBar = MutableLiveData<String?>(null)
    val snackBar: LiveData<String?>
        get() = _snackBar

    private var _category = MutableLiveData<CategoryEnum>().apply {
        value = CategoryEnum.ARTICLES
    }
    val category : LiveData<CategoryEnum>
        get() = _category

    init {
        fetchPosts(Search(_category.value?.toString() ?: CategoryEnum.ARTICLES.value))
    }

    fun fetchPosts(search: Search) {
        viewModelScope.launch {
            latestPostUseCase(search)
                .onStart {
                    _listPost.postValue(PostState.Loading)
                    showProgressBar()
                }
                .catch {
                    with(RemoteException(SERVICE_UNAVAILABLE)){
                        _listPost.postValue(PostState.Error(this))
                        _snackBar.value = this.message
                    }
                }
                .collect {
                    it.data?.let { posts ->
                        _listPost.value = PostState.Success(posts)
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                    _category.value = enumValueOf<CategoryEnum>(search.type.uppercase())
                }
        }
    }

    fun searchPostsTitleContains(searchString : String) =
        fetchPostsTitleContains(Search(_category.value.toString(),searchString))

    private fun fetchPostsTitleContains(search: Search) {
        viewModelScope.launch {
            searchTitleUseCase(search)
                .onStart {
                    _listPost.postValue(PostState.Loading)
                }
                .catch {
                    with(RemoteException(SERVICE_UNAVAILABLE)){
                        _listPost.postValue(PostState.Error(this))
                        _snackBar.value = this.message
                    }
                }
                .collect {
                    it.data?.let { posts ->
                        _listPost.value = PostState.Success(posts)
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                    _category.value = enumValueOf<CategoryEnum>(search.type.uppercase())
                }
        }
    }

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    fun hideSnackBar() {
        _snackBar.value = null
    }

    val showText = Transformations.map(listPost) { state ->
        when (state) {
            PostState.Loading -> {
                "Carregando as noticias.\n Espere um instante"
            }
            is PostState.Success -> EMPTY_MESSAGE
            is PostState.Error -> {
                "Peço desculpas\n Tente novamente mais tarde"
            }
        }
    }
    companion object {
        private const val SERVICE_UNAVAILABLE = "Serviço indisponível"
        private const val EMPTY_MESSAGE = ""
    }
}
