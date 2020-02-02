package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiClient
import com.example.newsapp.api.callbacks.NewsResponseCallBack
import com.example.newsapp.model.News
import retrofit2.Response

class NewsViewModel : ViewModel() {
    var apiClient: ApiClient? = ApiClient.Builder().create()
    var news : MutableLiveData<News?> = MutableLiveData<News?>()

    fun getNewsResponse() : MutableLiveData<News?> {
        apiClient?.getNewsResponse(object: NewsResponseCallBack {
            override fun onError(error: String) {
                Log.e("error", error)
            }


            override fun onFailure(error: String) {
                Log.e("error", error)
            }

            override fun onSuccess(response: Response<News>) {
                news.postValue(response.body())
            }
        })

        return news
    }

}