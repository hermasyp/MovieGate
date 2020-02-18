package com.catnip.mgfavapp.ui

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.mgfavapp.R
import com.catnip.mgfavapp.model.Favorite
import com.catnip.mgfavapp.model.Favorite.Companion.CONTENT_URI
import com.catnip.mgfavapp.utils.ContentCursorMapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var movies: MutableList<Favorite>
    private lateinit var favoriteListAdapter: FavoriteListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupContentObserver()
    }

    private fun setupContentObserver() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val mObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                fetchContentAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)
        fetchContentAsync()
        setupList()
    }


    private fun fetchContentAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val defferContents = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                ContentCursorMapper.mapCursorToContent(cursor)
            }
            val contents = defferContents.await()
            if (contents.size > 0) {
                movies = contents
                favoriteListAdapter.items = movies
            }
        }
    }

    private fun setupList() {
        rv_favorites.layoutManager = LinearLayoutManager(this)
        rv_favorites.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        favoriteListAdapter = FavoriteListAdapter {
            showToast(it?.title)
        }
        rv_favorites.adapter = favoriteListAdapter
    }

    private fun showToast(msg: String?){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

}
