package com.chslcompany.spaceflightnews.ui.home

import androidx.lifecycle.*
import com.chslcompany.spaceflightnews.core.PostState
import com.chslcompany.spaceflightnews.core.RemoteException
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    private val _listPost = MutableLiveData<PostState>()
    val listPost: LiveData<PostState>
        get() = _listPost

    private val _progressBarVisible = MutableLiveData(false)
    val progressBar: LiveData<Boolean>
        get() = _progressBarVisible

    private var _snackBar = MutableLiveData<String?>(null)
    val snackBar: LiveData<String?>
        get() = _snackBar

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(PostState.Loading)
                    showProgressBar()
                }
                .catch {
                    val exception = RemoteException(SERVICE_UNAVAILABLE)
                    _listPost.postValue(PostState.Error(exception))
                    _snackBar.postValue(exception.message)
                }
                .collect { posts ->
                    _listPost.postValue(PostState.Success(posts))
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

    companion object {
        private const val SERVICE_UNAVAILABLE = "Serviço indisponível"
    }

    val showText = Transformations.map(listPost){ state->
        when (state) {
            PostState.Loading -> {
               "Carregando as noticias.\n Espere um instante"
            }
            is PostState.Success -> ""
            is PostState.Error -> {
               "Peço desculpas\n Tente novamente mais tarde"
            }
        }
    }

}
