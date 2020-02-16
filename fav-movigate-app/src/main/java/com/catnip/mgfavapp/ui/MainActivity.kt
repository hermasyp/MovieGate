package com.catnip.mgfavapp.ui

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log.d
import android.widget.Toast
import com.catnip.mgfavapp.R
import com.catnip.mgfavapp.model.Favorite.Companion.CONTENT_URI
import com.catnip.mgfavapp.utils.ContentCursorMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupContentObserver()
    }

    private fun setupContentObserver(){
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val mObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                fetchContentAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)

    }

    private fun fetchContentAsync() {
        GlobalScope.launch(Dispatchers.IO) {
            val defferedContents = async (Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI,null,null,null,null)
                ContentCursorMapper.mapCursorToContent(cursor)
            }
            val contents = defferedContents.await()
            if(contents.size > 0){
                d(MainActivity::class.java.simpleName,contents.size.toString())
            }
        }
    }
}
