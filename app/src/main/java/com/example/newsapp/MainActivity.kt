package com.example.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsFeedAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.utils.NetworkUtils
import com.example.newsapp.viewmodel.NewsViewModel


class MainActivity : AppCompatActivity() {
    lateinit var newsViewModel : NewsViewModel
    lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        newsViewModel  = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        mainActivityBinding.setViewModel(newsViewModel)
        if(NetworkUtils().isNetworkConnected(this)) {
            SubscribeToNewsResponse()
        } else {
            Toast.makeText(this, "Connect to Internet and try again!!", Toast.LENGTH_LONG).show()
        }
    }

    fun SubscribeToNewsResponse() {
        newsViewModel.getNewsResponse().observe(this, Observer {
            mainActivityBinding.newsRecycler.layoutManager = LinearLayoutManager(this)
            mainActivityBinding.newsRecycler.adapter =
                it?.articles?.let { it1 -> NewsFeedAdapter(it1,this) }
        })
    }

}
