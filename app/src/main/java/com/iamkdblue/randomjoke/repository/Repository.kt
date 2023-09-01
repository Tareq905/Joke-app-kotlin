package com.fitway.anime.activity.repository

import com.fitway.anime.activity.api.ApiService
import com.iamkdblue.randomjoke.api.SafeApiRequest
import com.iamkdblue.randomjoke.model.JokeResponse

class Repository:SafeApiRequest() {

    suspend fun getRandomJokes(apiService: ApiService): JokeResponse {
        return apiRequest { apiService.getRandomJokes() }
    }
}