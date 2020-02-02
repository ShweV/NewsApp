package com.example.newsapp.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.NewsDetailsActivity
import com.example.newsapp.R
import com.example.newsapp.model.Articles
import kotlinx.android.synthetic.main.news_adapter_layout.view.*

class NewsFeedAdapter(val articleList : List<Articles>, val context: Context) : RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.news_adapter_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.author?.text = articleList.get(position).author
        holder?.source?.text = articleList.get(position).source.name
        holder?.title?.text = articleList.get(position).title
        Glide.with(context)
            .load(articleList.get(position).urlToImage)
            .into(holder?.image)

        holder?.newsItem.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v:View) {
                val i  = Intent(context, NewsDetailsActivity::class.java)
                i.putExtra("url",articleList.get(position).url)
                context.startActivity(i)
            }
        })
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val author = view.text_author
        val title = view.text_title
        val source = view.text_source
        val image = view.news_image
        val newsItem = view.news_item
    }

}