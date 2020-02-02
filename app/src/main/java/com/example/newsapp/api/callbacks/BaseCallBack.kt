package com.example.newsapp.api.callbacks

import retrofit2.Response

interface BaseCallBack<News, String> {
     fun onSuccess(response: Response<News>)
     fun onError(error:String)
}