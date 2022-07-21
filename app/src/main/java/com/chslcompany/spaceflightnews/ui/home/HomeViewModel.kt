package com.chslcompany.spaceflightnews.ui.home

import androidx.lifecycle.*
import com.chslcompany.spaceflightnews.core.CategoryEnum
import com.chslcompany.spaceflightnews.core.PostState
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetLatestPostsUseCase) : ViewModel(),
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
    val category : LiveData<CategoryEnum> = _category

    init {
        fetchPosts(_category.value ?: CategoryEnum.ARTICLES)
    }

    fun fetchPosts(categoryEnum: CategoryEnum) {
        viewModelScope.launch {
            useCase(categoryEnum.value)
                .onStart {
                    _listPost.postValue(PostState.Loading)
                    showProgressBar()
                }
                .catch {
                    val exception = RemoteException(SERVICE_UNAVAILABLE)
                    _listPost.postValue(PostState.Error(exception))
                    _snackBar.value = exception.message
                }
                .collect { posts ->
                    _listPost.value = PostState.Success(posts)
                    _category.value = enumValueOf<CategoryEnum>(categoryEnum.value.uppercase())
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
