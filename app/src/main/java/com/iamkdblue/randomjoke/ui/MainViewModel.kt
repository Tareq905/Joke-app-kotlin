package com.iamkdblue.randomjoke.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitway.anime.activity.api.ApiService
import com.fitway.anime.activity.repository.Repository
import com.iamkdblue.randomjoke.model.JokeResponse
import com.iamkdblue.randomjoke.util.ApiException
import com.iamkdblue.randomjoke.util.NoInternetException
import kotlinx.coroutines.*

class MainViewModel(
    val repository: Repository,
    val apiService: ApiService
) : ViewModel() {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _jokesList = MutableLiveData<JokeResponse>()
    private val _lodeMoreJokesList = MutableLiveData<JokeResponse>()
    private val _status = MutableLiveData<String>()

    val jokesLiveData: LiveData<JokeResponse>
        get() = _jokesList

    val lodeMoreJokesList: LiveData<JokeResponse>
        get() = _lodeMoreJokesList

    val status :LiveData<String>
        get() = _status

    init {
        getJokesFromApi()
    }

    private fun getJokesFromApi() {

        uiScope.launch {
            try{
                _jokesList.value = getJokes()
            }
            catch (noInternetException: NoInternetException) {
                _status.value = noInternetException.message
            }
            catch (apiException: ApiException)
            {
                _status.value = apiException.message
            }
        }
    }

    public fun loadMoreJokes() {
        uiScope.launch {
            try {
                _lodeMoreJokesList.value = getJokes()
            } catch (noInternetException: NoInternetException) {
                _status.value = noInternetException.message
            }
            catch (apiException: ApiException)
            {
                _status.value = apiException.message
            }
        }
    }

    private suspend fun getJokes(): JokeResponse? {
        return withContext(Dispatchers.IO)
        {
            //_fullScreenVideoList.value = repository.getFullScreenVideo()
            repository.getRandomJokes(apiService)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}