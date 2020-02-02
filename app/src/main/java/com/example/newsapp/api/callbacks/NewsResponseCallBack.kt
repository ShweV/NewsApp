package com.example.newsapp.api.callbacks

import com.example.newsapp.model.News

interface NewsResponseCallBack : BaseCallBack<News, String> {
    fun onFailure(error: String)
}