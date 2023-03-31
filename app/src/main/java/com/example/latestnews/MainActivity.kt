package com.example.latestnews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latestnews.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException


const val TAG = "Main Activity"

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var bind: ActivityMainBinding
    private lateinit var adapt: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setuprecycleview()

        //coroutine
        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenCreated{
            bind.progressBar.isVisible = true
            val response = try {
            RetrofitInstance.api.get("bbc-news",100,"debf2e87b75e4057bb984afeb6abfa36")
            } catch (_: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                bind.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (_: HttpException) {
                Log.e(TAG, "HttpException, unexpected response ")
                bind.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                bind.progressBar.isVisible = false
                adapt.todo=response.body()!!.articles
                //only needed to refer to the list i need in json object
                //i worked hard ;)
            } else {
                Log.e(TAG, "Response not successful")
                bind.progressBar.isVisible = false
            }
        }

    }

    private fun setuprecycleview() {
        bind.main.apply {
            adapt = Adapter(this@MainActivity)
            adapter = adapt
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onItemClicked(items: Article) {
        //Toast.makeText(this,"Hello something clicked",Toast.LENGTH_LONG).show()
        val url = items.url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

}

