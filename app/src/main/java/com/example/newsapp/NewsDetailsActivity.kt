package com.example.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.utils.NetworkUtils
import kotlinx.android.synthetic.main.news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.transition.fadein, R.transition.fadeout)
        setContentView(R.layout.news_details)
        if(NetworkUtils().isNetworkConnected(this)) {
            webview.getSettings().setJavaScriptEnabled(true)
            webview.loadUrl(intent.getStringExtra("url"))
        } else {
            Toast.makeText(this, "Connect to Internet and try again!!", Toast.LENGTH_LONG).show()
        }
    }
}