package com.example.ftmusicapp.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

object ApiErrorHandler {

    fun <T> getErrorMessage(response: Response<T>): String {
        return try {
            val errorBody = response.errorBody()?.string()

            if (!errorBody.isNullOrBlank()) {
                val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)

                jsonObject?.get("mensaje")?.asString
                    ?: "HTTP error: ${response.code()}"
            } else {
                "HTTP error: ${response.code()}"
            }
        } catch (ex: Exception) {
            "HTTP error: ${response.code()}"
        }
    }
}