package com.iamkdblue.randomjoke.api

import com.iamkdblue.randomjoke.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val mesaage = StringBuilder()

            error?.let {
                try {
                    mesaage.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    mesaage.append("\n")
                }
            }
            mesaage.append("Error Code:${response.code()}")
            throw ApiException(mesaage.toString())
        }
    }

}