package com.example.newsapp.api

import com.example.newsapp.api.callbacks.BaseCallBack
import com.example.newsapp.api.callbacks.NewsResponseCallBack
import com.example.newsapp.model.News
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class ApiClient(builder: Builder) {
     var apiService: ApiService? = builder.apiService


    fun getNewsResponse(newsResponseCallback: NewsResponseCallBack) {
        val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val call = apiService?.getResponse(date ,"d3f4c711016441139ea16f3ab59ca0d0")
        call?.enqueue(object : Callback<News> {

            override fun onResponse(@NotNull call: Call<News>, @NotNull response: Response<News>) {
                if (response.isSuccessful()) {
                    response.body()?.let { newsResponseCallback.onSuccess(response) }
                } else {
                    onErrorResponse(
                        Objects.requireNonNull(response.errorBody())!!,
                        newsResponseCallback
                    )
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                t.message?.let {
                    newsResponseCallback.onError(call.toString())//To change body of created functions use File | Settings | File Templates.
                }
            }
        })
    }

    private fun <K> onErrorResponse(errorBody: ResponseBody, callback: BaseCallBack<K, String>) {
        var errorMessage = "Something Went wrong!! Please try again later."
        try {
            errorMessage = errorBody.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        callback.onError(errorMessage)
    }

    class Builder {
        var apiService: ApiService? = null

        private val okhttp3Client: OkHttpClient
            get() {
                val dispatcher = Dispatcher(Executors.newFixedThreadPool(20))
                dispatcher.setMaxRequests(20)
                dispatcher.setMaxRequestsPerHost(1)
                return OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .connectionPool(ConnectionPool(100, 30, TimeUnit.SECONDS))
                    .build()
            }

        fun create(): ApiClient {
            apiService = getRetrofit(okhttp3Client).create(ApiService::class.java)
            return ApiClient(this)
        }

        private fun getRetrofit(okhttp3Client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(ApiConstants.END_POINT_API_WEB_RESPONSE)
                .client(okhttp3Client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


}